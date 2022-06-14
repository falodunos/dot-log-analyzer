package com.dot.file.reader.config.spring;

import com.dot.file.reader.persistence.model.UserAccess;
import com.dot.file.reader.service.util.Report;
import com.dot.file.reader.service.util.ReportFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

@Slf4j
public class UserAccessLogProcessor implements ItemProcessor<UserAccess, UserAccess> {

    @Override
    public UserAccess process(UserAccess userAccess) throws Exception {

        Date date = new Date();

        userAccess.setCreatedAt(date);
        userAccess.setUpdatedAt(date);

        Report report = ReportFactory.getInstance();
        report.update(userAccess);

        return userAccess;
    }

}
