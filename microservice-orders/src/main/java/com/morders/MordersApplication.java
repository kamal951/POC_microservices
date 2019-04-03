package com.morders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class MordersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MordersApplication.class, args);
	}
}
