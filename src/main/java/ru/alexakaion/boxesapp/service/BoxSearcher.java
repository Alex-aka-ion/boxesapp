package ru.alexakaion.boxesapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexakaion.boxesapp.model.Box;
import ru.alexakaion.boxesapp.model.Item;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxSearcher {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BoxRepository boxRepository;

    private List<Integer> boxIds;

    public List<Integer> searchBoxByColorInsideBoxes(Integer parentBoxId, String color) {
        this.boxIds = new ArrayList<>(List.of(parentBoxId));
        searchChildBoxesRecursive(parentBoxId);

        List<Item> items = itemRepository.getItemsByBoxIdsAndColor(this.boxIds,color);

        return items.stream().map(Item::getId).collect(Collectors.toList());
    }

    private void searchChildBoxesRecursive(Integer parentBoxId) {
        List<Box> childBoxes = boxRepository.getChildBoxes(parentBoxId);

        for (Box childBox: childBoxes) {
            this.boxIds.add(childBox.getId());
            searchChildBoxesRecursive(childBox.getId());
        }
    }
}
