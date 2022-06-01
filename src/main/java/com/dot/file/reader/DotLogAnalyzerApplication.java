package com.dot.file.reader;

import com.dot.file.reader.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DotLogAnalyzerApplication implements CommandLineRunner {

	@Autowired
	AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(DotLogAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("AppConfig :: " + appConfig.getRuntimeConfigs().toString());
	}
}
