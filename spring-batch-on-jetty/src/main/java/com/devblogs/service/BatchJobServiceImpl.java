package com.devblogs.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("batchJobService")
public class BatchJobServiceImpl implements BatchJobService {
	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String lanchJob() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("inputResource", "classpath:/input/products.zip")
				.addString("targetDirectory", "./target/importproductsbatch/").addString("targetFile", "products.txt")
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();
		
		jobLauncher.run(job, jobParameters);
		
		StringBuilder builder = new StringBuilder();
		List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM products");
		for (Map<String, Object> map : list) {
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				builder.append(map.get(key) + "|");
			}
			builder.append("<br>");
		}
		return builder.toString();
	}
}