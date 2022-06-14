package com.dot.file.reader.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunnableObject { //implements Runnable{

    TextFileProcessorService textFileProcessorService;

    public RunnableObject(TextFileProcessorService textFileProcessorService) {
        this.textFileProcessorService = textFileProcessorService;
    }

//    @Override
    public void run() {

            try {
                log.info("Started Database Uploading Thread");
                Thread.sleep(1000); // wait 1 seconds

                textFileProcessorService.clearTable();
                textFileProcessorService.importTextFileToDatabase();

                log.info("Completed Database Uploading Thread");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }
}
