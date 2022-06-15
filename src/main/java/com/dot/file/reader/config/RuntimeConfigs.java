package com.dot.file.reader.config;

import com.dot.file.reader.exception.InvalidParameterException;
import com.dot.file.reader.persistence.model.enums.request.Duration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Data
@Slf4j
public class RuntimeConfigs {

    private String start;
    private String duration;

    private String rateLimit;
    public RuntimeConfigs() {
    }

    public RuntimeConfigs(String startDateTime, String duration, String rateLimit) {
        this.start = startDateTime;
        this.duration = duration;
        this.rateLimit = rateLimit;

        if (!isValidDateTime()) {
            // throw exception here
            throw new InvalidParameterException("Oops! Invalid date format!");
        }

        if (!isValidRateLimit()) {
            // throw exception here
            throw new InvalidParameterException("Oops! Invalid rate limit, please use 200 or 500.");
        }

        if (!isValidDuration()) {
            // throw exception here
            throw new InvalidParameterException("Oops! Invalid duration, please use HOURLY or DAILY.");
        }
    }

    public boolean isValidDuration() {
        return Duration.HOURLY.getCode().equalsIgnoreCase(this.duration) ||
                Duration.DAILY.getCode().equalsIgnoreCase(this.duration);
    }

    public boolean isValidRateLimit() {
        log.info("Supplied Rate Limit :: " + rateLimit);
        System.out.println("Supplied Rate Limit :: " + rateLimit);
        return rateLimit != null && String.valueOf(rateLimit).matches("[0-9]+")
                && (Integer.parseInt(rateLimit) == 200 || Integer.parseInt(rateLimit) == 500);
    }

    private boolean isValidDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
        sdf.setLenient(false);
        boolean status = true;
        try {
            sdf.parse(this.start);
        } catch (ParseException e) {
            status = false;
        }
        return status;
    }

    @Override
    public String toString() {
        return "RuntimeConfigs{" +
                "start='" + start + '\'' +
                ", duration='" + duration + '\'' +
                ", rateLimit='" + rateLimit + '\'' +
                '}';
    }
}
