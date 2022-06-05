package ru.alexakaion.boxesapp.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class H2BackupRepositoryImpl implements H2BackupRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void makeH2Backup(String backupFilename) {
        String query = "SCRIPT TO '" + backupFilename + "'";
        log.debug(query);
        jdbcTemplate.execute(query);
    }
}
