package com.zilker.onlinejobsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;


@EnableAutoConfiguration
@ComponentScan
public class MainClass {

		@RequestMapping("/hello")
		String home() {
			return "Hello World!";
		}

		public static void main(String[] args) {
			SpringApplication.run(MainClass.class, args);
		}
	}


