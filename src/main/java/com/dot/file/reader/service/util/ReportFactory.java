package com.dot.file.reader.service.util;

public class ReportFactory {
    private static Report report;

    public static Report getInstance() {
        if (report == null) {
            report = new Report();
        }

        return report;
    }
}
