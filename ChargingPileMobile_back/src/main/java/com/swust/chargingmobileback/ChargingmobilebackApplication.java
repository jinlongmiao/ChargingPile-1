package com.swust.chargingmobileback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.swust.chargingmobileback.dao.**")
public class ChargingmobilebackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChargingmobilebackApplication.class, args);
	}
}
