package com.dot.file.reader.persistence.model;

import com.dot.file.reader.persistence.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Slf4j
@Entity
@Table(name="USER_ACCESS_LOG")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccess extends DateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private String loginTime;
    private String httpMethod;
    private String responseCode;
    private String agent;

    public UserAccess() {}

    @Override
    public String toString() {
        return "Access{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", agent='" + agent + '\'' +
                '}';
    }
}
