package com.swust.chargingmobileback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.swust.chargingmobileback.dao.**")
public class ChargingmobilebackApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ChargingmobilebackApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ChargingmobilebackApplication.class, args);
	}
}
