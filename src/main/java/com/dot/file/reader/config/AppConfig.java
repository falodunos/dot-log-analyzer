package com.dot.file.reader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class AppConfig {

    private String name;
    private String environment;
    private String uploadDir;
    private String duration;

}