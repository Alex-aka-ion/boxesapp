package ru.alexakaion.boxesapp.repository;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CommonRepository {
    void makeH2Backup(String backupFilename);
}
