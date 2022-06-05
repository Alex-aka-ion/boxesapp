package ru.alexakaion.boxesapp.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.alexakaion.boxesapp.model.Box;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoxRepositoryTest {
    @Autowired
    private BoxRepository boxRepository;

    @Test
    void testRecursiveQueryTest() {
        List<Box> boxes = boxRepository.getAllChildBoxesRecursive(1);

        assertEquals(Arrays.asList(
                new Box(1, null),
                new Box(3,1),
                new Box(6,1)
        ), boxes);
    }
}
