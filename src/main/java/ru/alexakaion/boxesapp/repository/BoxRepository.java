package ru.alexakaion.boxesapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexakaion.boxesapp.model.Box;

import java.util.List;

@Transactional(readOnly = true)
public interface BoxRepository extends CrudRepository<Box, Integer> {
    @Query("SELECT b FROM Box b WHERE b.containedIn = :boxId")
    List<Box> getChildBoxes(Integer boxId);
}
