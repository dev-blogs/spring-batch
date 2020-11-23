package com.example.integration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

public class ProductImportToJobLauncherRequestHandler {
	public JobLaunchRequest adapt(File products) {
		String importId = FilenameUtils.getBaseName(products.getAbsolutePath());
		Map<String, String> jobParameters = new HashMap<>();
		jobParameters.put("imoprtId", importId);
		jobParameters.put("importFile", products.getAbsolutePath());
		return new JobLaunchRequest("importProducts", jobParameters);
	}
}