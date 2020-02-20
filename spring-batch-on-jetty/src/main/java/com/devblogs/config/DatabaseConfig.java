package com.devblogs.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan("com.devblogs.dao")
public class DatabaseConfig {
	@Bean
	@Qualifier("dataSource")
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder
			.setType(EmbeddedDatabaseType.H2)
			.addScript("db/create-db.sql")
			.addScript("org/springframework/batch/core/schema-h2.sql")
			.build();
	}
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") DataSource ds) {
		return new NamedParameterJdbcTemplate(ds);
	}
}