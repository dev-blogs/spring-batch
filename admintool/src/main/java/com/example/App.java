package com.example;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class App {
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8084);
		connector.setHost("127.0.0.1");
		server.addConnector(connector);

		WebAppContext wac = new WebAppContext();
		wac.setContextPath("/springbatchadmin");
		wac.setWar("./src/main/webapp");
		server.setHandler(wac);
		server.setStopAtShutdown(true);
		
		server.start();
        
        System.out.println("**** Spring Batch Admin launched");
	}
}