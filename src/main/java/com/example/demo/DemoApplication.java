package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.controller.BuildingsController;
import com.example.entity.Building;
import com.example.repository.BuildingsRepository;

@SpringBootApplication(scanBasePackages={
		"com.example.controller", "com.example.entity","com.example.repository","com.example.exception"})
@EnableJpaRepositories(basePackageClasses = BuildingsRepository.class)
@EntityScan("com.example.entity")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
