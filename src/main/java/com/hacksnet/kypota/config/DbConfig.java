package com.hacksnet.kypota.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DbConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.db01.datasource")
	public DataSource dataSource() {
	    return DataSourceBuilder.create().build();
	}

}
