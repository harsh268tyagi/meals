package com.assignment.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MealRecommendationApplication {

	private final static Logger logger = LoggerFactory.getLogger(MealRecommendationApplication.class);
	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(MealRecommendationApplication.class, args);
		Environment env = applicationContext.getEnvironment();
		String port = env.getProperty("server.port", "8080");
		logger.info("Application started on port: {} successfully!!", port);



	}

}
