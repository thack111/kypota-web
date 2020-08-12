package com.hacksnet.kypota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class KypotaApplication  {
	
	
	public static void main(String[] args) {
		SpringApplication.run(KypotaApplication.class, args);
		
		System.out.println("Application starting");
	}

}
