//package com.hacksnet.kypota.config;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.hacksnet.kypota.controller.HomeController;
//import com.hacksnet.kypota.service.TestService;
//
//
//
//@Component
//@Configuration
//public class JerseyConfig extends ResourceConfig{
//	
//	
//	@Bean
//	@Primary
//	public ObjectMapper objectMapper() {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
//		objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
//		return objectMapper;
//	}
//	
//	@Autowired
//	public JerseyConfig() {
//		register(TestService.class);
//		//register(HomeController.class);
//		/*
//		 * register(CoAssistService.class); packages("com.att.netopstools.model");
//		 * register(JacksonFeature.class);
//		 */
//	}
//
//}
