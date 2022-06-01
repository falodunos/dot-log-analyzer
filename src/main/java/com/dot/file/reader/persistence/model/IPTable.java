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
@Table(name="BLOCKED_IP_TABLE")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IPTable extends DateAudit {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;

    public IPTable() {}

    @Override
    public String toString() {
        return "Access{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
