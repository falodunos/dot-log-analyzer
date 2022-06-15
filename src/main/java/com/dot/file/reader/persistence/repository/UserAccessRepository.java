package com.dot.file.reader.persistence.repository;

import com.dot.file.reader.persistence.model.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Integer> {
}
