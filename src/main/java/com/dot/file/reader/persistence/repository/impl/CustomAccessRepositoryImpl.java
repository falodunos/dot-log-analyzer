package com.dot.file.reader.persistence.repository.impl;

import com.dot.file.reader.persistence.repository.CustomAccessRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomAccessRepositoryImpl implements CustomAccessRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int truncateTable() {
        return entityManager.createQuery("TRUNCATE TABLE user_access_log").executeUpdate();
    }
}
