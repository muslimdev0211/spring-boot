package com.clean.code.springboot56;

import com.clean.code.springboot56.domain.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class SpringBoot56Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot56Application.class, args);
	}

//	@Scheduled(fixedRate = 3000L)
//	public void start (){
//		System.out.println("New rate" + new Date());
//	}
}
