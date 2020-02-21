package com.devblogs.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.devblogs.service.BatchJobService;

@Controller
public class JobLauncherController {
	@Autowired
	private BatchJobService batchJobService;
	
	@RequestMapping(value="joblauncher", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@RequestParam String job, HttpServletRequest request) throws Exception {
		String lanchJob = batchJobService.lanuchJob();
		System.out.println(lanchJob);
	}
}