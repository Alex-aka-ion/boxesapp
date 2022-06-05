package ru.alexakaion.boxesapp.config;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alexakaion.boxesapp.model.Box;
import ru.alexakaion.boxesapp.model.Item;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.H2BackupRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(args={"classpath:test.xml"})
class CustomXmlReaderTest {
    @MockBean
    private BoxRepository boxRepository;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private H2BackupRepository h2BackupRepository;

    @Captor
    ArgumentCaptor<Box> boxCaptor;
    @Captor
    ArgumentCaptor<Item> itemCaptor;

    @Test
    void testRun() {
        verify(boxRepository, times(3)).save(boxCaptor.capture());
        verify(itemRepository, times(6)).save(itemCaptor.capture());
        verify(h2BackupRepository, times(1)).makeH2Backup(ArgumentMatchers.eq("backup.sql"));

        List<Box> resultBoxes = boxCaptor.getAllValues();
        List<Item> resultItems = itemCaptor.getAllValues();

        assertEquals(
                Arrays.asList(
                        new Box(1,null),
                        new Box(3,1),
                        new Box(6,1)
                ),
                resultBoxes
        );

        assertEquals(
                Arrays.asList(
                        new Item(1,1,null),
                        new Item(2,1,"red"),
                        new Item(5,1,null),
                        new Item(3,3,"red"),
                        new Item(4,3,"black"),
                        new Item(6,null,null)
                ),
                resultItems
        );
    }
}