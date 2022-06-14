package com.dot.file.reader.persistence.model;

import com.dot.file.reader.persistence.model.audit.DateAudit;
import com.dot.file.reader.validator.date.ValidDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Slf4j
@Entity
@Table(name="USER_ACCESS_LOG")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccess extends DateAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ValidDate
    @Column(name = "date", nullable = false, columnDefinition = "varchar(50)")
    private String date;

    @NotBlank(message = "Please provide a valid IP")
    @Column(name = "ip", nullable = false, columnDefinition = "varchar(25)")
    private String ip;

    @NotBlank(message = "Please provide a valid request")
    @Column(name = "request", nullable = false, columnDefinition =  "varchar(255)")
    private String request;

    @NotBlank(message = "Please provide a valid status")
    @Column(name = "status", nullable = false, columnDefinition = "varchar(5)")
    private String status;

    @NotBlank(message = "Please provide a valid user agent")
    @Column(name = "user_agent", nullable = false, columnDefinition = "TEXT")
    private String userAgent;

    public UserAccess() {}

    @Override
    public String toString() {
        return "UserAccess{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", ip='" + ip + '\'' +
                ", request='" + request + '\'' +
                ", status='" + status + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
