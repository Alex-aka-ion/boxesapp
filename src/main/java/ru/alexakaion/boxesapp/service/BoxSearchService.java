package ru.alexakaion.boxesapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexakaion.boxesapp.model.Box;
import ru.alexakaion.boxesapp.model.Item;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoxSearchService {
    private final ItemRepository itemRepository;
    private final BoxRepository boxRepository;

    public List<Integer> searchBoxByColorInsideBoxes(Integer parentBoxId, String color) {
        List<Box> boxes = boxRepository.getAllChildBoxesRecursive(parentBoxId);

        List<Item> items = itemRepository.getItemsByBoxIdsAndColor(
                boxes.stream().map(Box::getId).collect(Collectors.toList()),
                color
        );

        return items.stream().map(Item::getId).collect(Collectors.toList());
    }
}
