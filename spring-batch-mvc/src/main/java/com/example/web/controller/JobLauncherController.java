package com.example.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.repository.jdbc.ProductImportRepository;

@Controller
public class JobLauncherController {
	@Autowired
	private ProductImportRepository productImportRepository;
	
	@RequestMapping(value = "launch", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@RequestParam String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("echo: " + name + "<br>");
		
		productImportRepository.createProductImport(name);
		
		response.getWriter().println("session=" + request.getSession(true).getId());
	}
}