package ru.alexakaion.boxesapp.repository;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface H2BackupRepository {
    void makeH2Backup(String backupFilename);
}
