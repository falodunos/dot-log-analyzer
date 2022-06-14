package com.dot.file.reader.service;

import com.dot.file.reader.persistence.model.IPTable;
import com.dot.file.reader.persistence.repository.IPTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IPTableService {

    @Autowired
    IPTableRepository ipTableRepository;

    public IPTable save(String ip) {

        IPTable ipTable = new IPTable();
        ipTable.setIpAddress(ip);

        return ipTableRepository.save(ipTable);
    }
}
