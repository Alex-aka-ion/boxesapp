package ru.alexakaion.boxesapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexakaion.boxesapp.model.Item;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemRepository extends CrudRepository<Item, Integer> {
    @Query("SELECT i FROM Item i WHERE i.containedIn in (:boxIds) and i.color = :color")
    List<Item> getItemsByBoxIdsAndColor(List<Integer> boxIds, String color);
}
