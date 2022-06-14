package com.dot.file.reader.service.util;

import com.dot.file.reader.exception.InvalidParameterException;
import com.dot.file.reader.persistence.model.UserAccess;
import com.dot.file.reader.persistence.model.enums.request.Duration;
import com.dot.file.reader.service.IPTableService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Report {


    @Autowired
    IPTableService ipTableService;

    private static String startDateTime;
    private static Map<String, Integer> allIpMap;
    private static Map<String, Integer> defaultingIpMap;
    private String duration;
    private int rateLimit;

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        Report.startDateTime = startDateTime;
    }

    public int getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(int rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *
     * @return
     */
    public Map<String, Integer> getDefaultingIPs() {
        return defaultingIpMap;
    }

    /**
     *
     * @param key
     * @return
     */
    public static Map<String, Integer> updateAllIpMap(String key) {
        if (allIpMap == null) { allIpMap = new HashMap<>();}

        if (!allIpMap.containsKey(key)) {
            allIpMap.put(key, 1); // initialize first IP access in this hours
        } else {
            int dailyCount = allIpMap.get(key);
            dailyCount += 1;
            allIpMap.put(key, dailyCount); // increment current IP access in this hour
        }

        return allIpMap;
    }

    /**
     *
     * @param ip
     * @param accessCount
     */
    public static void updateDefaultingIpMap(String ip, int accessCount) {
        if (defaultingIpMap == null) {
            defaultingIpMap = new HashMap<>();
        }
        // keep the first instance in which IP access is beyond rate limit in one hour
        if (!defaultingIpMap.containsKey(ip)) defaultingIpMap.put(ip, accessCount);

        System.out.println("defaultingIpMap " + defaultingIpMap.toString());
        log.info("defaultingIpMap " + defaultingIpMap.toString());
    }

    /**
     * Update report method called from outside this domain
     * @param userAccess
     */
    public void update(UserAccess userAccess) {

        System.out.println("Stated Out With " + userAccess.toString());

        allIpMap = updateAllIpMap(userAccess.getIp());

        // time difference between first access and the current access
        long timeDifference = getTimeDifferenceInSeconds(userAccess, getStartDateTime());

        long durationSpan = duration.equalsIgnoreCase(Duration.DAILY.getCode()) ?
                 24 * 60 * 60 : 60 * 60; // either 60 * 60 = 3600 or 60 * 60 * 24 = 86400 seconds

        System.out.println("Supplied Duration " + duration + " Duration Span " + durationSpan);

        if (timeDifference >= durationSpan) {
            System.out.println("Add IP " + userAccess.getIp() + " to defaulting Map");
            startDateTime = userAccess.getDate(); // update start time to the current access time going forward

//            Map<String, Integer> defaulting = allIpMap.entrySet().stream().filter( e -> e.getValue() >= this.getRateLimit()).collect(Collectors.toMap(
//                    entry -> entry.getKey(),
//                    entry -> entry.getValue()));

            int accessCount = allIpMap.get(userAccess.getIp());
            System.out.println("Access Count " + accessCount + " Rate Limit " + getRateLimit());

            if (accessCount > getRateLimit()) {
                // log IP that made access beyond rate limit in an hour
                System.out.println("Defaulting IP " + userAccess.getIp() + " Default Count " + accessCount);
                updateDefaultingIpMap(userAccess.getIp(), accessCount);
                // Sample Result : Defaulting IP 192.168.51.205 Default Count 270

                //log defaulting IP address in IP table for review purposes
                try {
                    long id = ipTableService.save(userAccess.getIp()).getId();
                    System.out.println("IP '" + userAccess.getIp() + "' was logged in the DB with id '" + id + "'");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("Ignore Access Record for IP " + userAccess.getIp());
        }
    }

    /**
     *
     * @param userAccess
     * @param startDateTimeStr
     * @return
     */
    private long getTimeDifferenceInSeconds(UserAccess userAccess, String startDateTimeStr) {

        long startTime;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        long accessTime = LocalDateTime.parse(userAccess.getDate(), formatter).toDateTime().getMillis();

        try {
            startTime = getDate(startDateTimeStr).getTime();
        } catch (Exception ex) {
            startTime = LocalDateTime.parse(startDateTimeStr, formatter).toDateTime().getMillis();
        }

        long timeDifference = (accessTime - startTime) / 1000;
        System.out.println("timeDifference :: " + timeDifference + " seconds");

        return timeDifference;
    }

    private Date getDate(String startDateTime) {
        System.out.println("String startDateTime :: " + startDateTime);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
        sdf.setLenient(false);
        Date date;
        try {
            date = sdf.parse(startDateTime);
        } catch (ParseException e) {
            throw new InvalidParameterException("Oops! Invalid date format!");
        }
        return date;
    }
}
