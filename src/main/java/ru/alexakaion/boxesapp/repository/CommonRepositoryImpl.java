package ru.alexakaion.boxesapp.repository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alexakaion.boxesapp.config.CustomXmlReader;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class CommonRepositoryImpl implements CommonRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CommonRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void makeH2Backup(String backupFilename) {
        LOG.info("SCRIPT TO '" + backupFilename + "'");
        jdbcTemplate.execute("SCRIPT TO '" + backupFilename + "'");
    }
}
