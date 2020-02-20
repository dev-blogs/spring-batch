package com.devblogs.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import com.devblogs.service.BatchJobService;

@Component("testServlet")
public class TestServlet implements HttpRequestHandler {
	@Autowired
	private BatchJobService batchJobService;

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		try {
			String result = batchJobService.lanchJob();
			response.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().println("session=" + request.getSession(true).getId());
	}
}