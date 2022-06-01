package com.dot.file.reader.service;

import com.dot.file.reader.service.util.AppConstants;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Service
public class TextFileProcessorService {

    /**
     * Read Text File
     * @return CSVReader
     * @throws FileNotFoundException
     */
    private CSVReader readFile() throws FileNotFoundException {
        CSVReader reader = new CSVReader(new FileReader(AppConstants.TARGET_FILE_PATH));
        return  reader;
    }

}
