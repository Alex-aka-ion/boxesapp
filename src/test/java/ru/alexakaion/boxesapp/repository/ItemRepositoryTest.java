package ru.alexakaion.boxesapp.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.alexakaion.boxesapp.model.Box;
import ru.alexakaion.boxesapp.model.Item;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    void testGetItemsByIdAndColor() {
        List<Item> items = itemRepository.getItemsByBoxIdsAndColor(Arrays.asList(1,3,6),"red");

        assertEquals(Arrays.asList(
                new Item(2, 1,"red"),
                new Item(3,3,"red")
        ), items);
    }
}
