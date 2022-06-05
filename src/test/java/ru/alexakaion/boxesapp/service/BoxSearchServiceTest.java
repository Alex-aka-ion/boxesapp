package ru.alexakaion.boxesapp.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alexakaion.boxesapp.model.Box;
import ru.alexakaion.boxesapp.model.Item;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(args = {"classpath:test.xml"})
class BoxSearchServiceTest {
    @MockBean
    ItemRepository itemRepository;

    @MockBean
    BoxRepository boxRepository;

    @Autowired
    BoxSearchService boxSearchService;

    @Test
    void testSearchBoxByColorInsideBoxes() {
        List<Box> returnBoxes = new ArrayList<>();
        returnBoxes.add(new Box(1, null));
        returnBoxes.add(new Box(3, 1));
        returnBoxes.add(new Box(6, 1));
        List<Integer> boxIds = new ArrayList<>(Arrays.asList(1, 3, 6));

        List<Item> returnItems = new ArrayList<>();
        returnItems.add(new Item(2, 1, "red"));
        returnItems.add(new Item(3, 3, "red"));

        doReturn(returnBoxes)
                .when(boxRepository)
                .getAllChildBoxesRecursive(1);

        doReturn(returnItems)
                .when(itemRepository)
                .getItemsByBoxIdsAndColor(boxIds, "red");

        assertEquals(
                Arrays.asList(2, 3),
                boxSearchService.searchBoxByColorInsideBoxes(1, "red")
        );
    }
}