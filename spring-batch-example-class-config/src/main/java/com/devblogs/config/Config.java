package com.devblogs.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import com.devblogs.batch.mapper.ProductFieldSetMapper;
import com.devblogs.batch.mapper.ProductJdbcItemWriter;
import com.devblogs.model.Product;

/**
 * https://docs.spring.io/spring-batch/docs/current/reference/html/step.html
 * https://stackoverflow.com/questions/22108315/how-can-i-configure-spring-batch-stepscope-using-java-based-configuration
 * https://docs.spring.io/spring-batch/docs/3.0.x/reference/html/configureStep.html
 * @author vostro
 *
 */
@Configuration
@EnableBatchProcessing(modular = true)
public class Config {
	private static final String JOB_NAME = "import";
	private static final String JOB_MASTER_STEP = "jobMasterStep";
	private static final String DECOMPRESS_STEP = "decompressStep";
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get(JOB_NAME)
				.start(decompress())
				.next(masterStep())
				.build();
	}
	
	@Bean
	public Step decompress() {
		return stepBuilderFactory.get(DECOMPRESS_STEP)
				.tasklet(decompressTasklet(null))
				.build();
	}
	
	@Bean
	public Step masterStep() {
		return stepBuilderFactory.get(JOB_MASTER_STEP)
				.<Product, Product>chunk(10)
				.reader(reader(null))
				.writer(writer())
				.faultTolerant()
				.skipPolicy(skipPolicy())
				.build();
	}
	
	@Bean
	public SkipPolicy skipPolicy() {
		return (t, sc) -> {
			if (t instanceof FlatFileParseException) {
				return true;
			}
			return false;
		};
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<Product> reader(@Value("#{jobParameters}") Map<String, Object> jobParameters) {
		String targetDirectory = (String) jobParameters.get("targetDirectory");
		String targetFile = (String) jobParameters.get("targetFile");
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		
		Resource resource = resourceLoader.getResource("file:./" + targetDirectory + targetFile);
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("ID", "NAME", "DESCRIPTION", "PRICE");
		
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new ProductFieldSetMapper());
		
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		reader.setLinesToSkip(1);
		reader.setResource(resource);
		reader.setLineMapper(lineMapper);
		return reader;
	}
	
	@Bean
	public ProductJdbcItemWriter writer() {
		ProductJdbcItemWriter writer = new ProductJdbcItemWriter();
		writer.setDataSource(dataSource);
		return writer;
	}
	
	@Bean
	@StepScope
	public Tasklet decompressTasklet(@Value("#{jobParameters}") Map<String, Object> jobParameters) {
		Resource inputResource = new ClassPathResource((String) jobParameters.get("inputResource"));
		String targetDirectory = (String) jobParameters.get("targetDirectory");
		String targetFile = (String) jobParameters.get("targetFile");
		return (sc, cc) -> {
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputResource.getInputStream()));
			File targetDirectoryAsFile = new File(targetDirectory);
			if (!targetDirectoryAsFile.exists()) {
				FileUtils.forceMkdir(targetDirectoryAsFile);
			}
			File target = new File(targetDirectory, targetFile);
			BufferedOutputStream dest = null;
			while (zis.getNextEntry() != null) {
				if (!target.exists()) {
					target.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(target);
				dest = new BufferedOutputStream(fos);
				IOUtils.copy(zis, dest);
				dest.flush();
				dest.close();
			}
			zis.close();
			if (!target.exists()) {
				throw new IllegalStateException("Could not decompress anything from the archive!");
			}
			return RepeatStatus.FINISHED;
		};
	}
}