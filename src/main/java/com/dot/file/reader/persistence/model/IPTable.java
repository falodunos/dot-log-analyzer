package com.dot.file.reader.persistence.model;

import com.dot.file.reader.persistence.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Slf4j
@Entity
@Table(name="BLOCKED_IP_TABLE")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IPTable extends DateAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", nullable = false, unique = true, columnDefinition = "varchar(20)")
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
