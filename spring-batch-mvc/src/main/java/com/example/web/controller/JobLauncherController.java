package com.example.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.integration.ProductImport;
import com.example.integration.ProductImportGateway;
import com.example.repository.jdbc.ProductImportRepository;

@Controller
public class JobLauncherController {
	@Autowired
	private ProductImportRepository productImportRepository;
	@Autowired
	private ProductImportGateway productImportGateway;
	
	@RequestMapping(value = "/product-imports/{importId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void launch(@PathVariable String importId, @RequestBody String content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("echo importId: " + importId + "<br>");
		response.getWriter().println("echo content: " + content + "<br>");
		
		productImportRepository.createProductImport(importId);
		productImportGateway.importProducts(content);
		
		response.getWriter().println("session=" + request.getSession(true).getId());
	}
	
	@RequestMapping(value="/product-imports/{importId}", method=RequestMethod.GET)
	@ResponseBody
	public ProductImport getProductImport(@PathVariable String importId) {
		return productImportRepository.get(importId);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No product import for this ID.")
	public void noImportFound() {
	}
}