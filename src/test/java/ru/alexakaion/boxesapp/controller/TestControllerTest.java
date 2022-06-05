package ru.alexakaion.boxesapp.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alexakaion.boxesapp.model.BoxSearchRequest;
import ru.alexakaion.boxesapp.service.BoxSearchService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(args={"classpath:test.xml"})
class TestControllerTest {
    @MockBean
    private BoxSearchService boxSearchService;

    @Test
    void testGetBoxIdsWithColor() {
        List<Integer> boxesList = new ArrayList<>(Arrays.asList(2,3));

        doReturn(boxesList)
                .when(boxSearchService)
                .searchBoxByColorInsideBoxes(1,"red");

        assertEquals(
                boxesList,
                boxSearchService.searchBoxByColorInsideBoxes(1,"red")
        );
    }
}