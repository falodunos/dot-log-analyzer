package com.dot.file.reader.persistence.repository;

import com.dot.file.reader.persistence.model.IPTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPTableRepository extends JpaRepository<IPTable, Integer> {
}
