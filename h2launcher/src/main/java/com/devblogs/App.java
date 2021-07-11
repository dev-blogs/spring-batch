package com.devblogs;

import java.sql.SQLException;

import org.h2.tools.Console;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) throws SQLException {
		new ClassPathXmlApplicationContext("classpath:context.xml");
		Console.main(args);
	}
}