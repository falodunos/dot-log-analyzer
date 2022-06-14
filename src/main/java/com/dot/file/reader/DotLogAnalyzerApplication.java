package com.dot.file.reader;

import com.dot.file.reader.config.AppConfig;
import com.dot.file.reader.config.RuntimeConfigs;
import com.dot.file.reader.service.TextFileProcessorService;
import com.dot.file.reader.service.util.Report;
import com.dot.file.reader.service.util.ReportFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@Slf4j
@EnableBatchProcessing
@SpringBootApplication
public class DotLogAnalyzerApplication implements CommandLineRunner {

	@Autowired
	TextFileProcessorService textFileProcessorService;

	@Autowired
	AppConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(DotLogAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			System.out.println("args " + Arrays.toString(args));
			System.out.println("AppConfig :: " + appConfig.getDuration());

			if (args.length > 0) {
				String startDateTime = args[0];
				String duration = args[1];
				String rateLimit = args[2];

				RuntimeConfigs runtimeConfigs = new RuntimeConfigs(startDateTime, duration, rateLimit);
				System.out.println("RuntimeConfigs :: " + runtimeConfigs);

				Report report = ReportFactory.getInstance();
				report.setDuration(duration);
				report.setRateLimit(Integer.parseInt(rateLimit));
				report.setStartDateTime(startDateTime);

				textFileProcessorService.clearTable();
				textFileProcessorService.importTextFileToDatabase();

				report = ReportFactory.getInstance();
				System.out.println(" Report :: " + report.getDefaultingIPs());
				System.out.println(" Report Size:: " + report.getDefaultingIPs().size());
			}

		} catch (Exception exception) {
			log.error("Application Error :: " + exception.getMessage());
			log.error("Application Error :: " + exception.getLocalizedMessage());

			exception.printStackTrace();
		}

	}
}
