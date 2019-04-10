package com.example.demoloadtestingeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DemoLoadTestingEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoLoadTestingEurekaServerApplication.class, args);
	}
}
