package ru.alexakaion.boxesapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.alexakaion.boxesapp.model.Box;

import java.util.List;

@Transactional(readOnly = true)
public interface BoxRepository extends CrudRepository<Box, Integer> {
    @Query(value = "WITH LINK(ID, CONTAINED_IN) AS \n" +
            "( SELECT ID, CONTAINED_IN FROM BOX WHERE ID = :startBoxId\n" +
            "    UNION ALL\n" +
            "  SELECT S2.ID, S2.CONTAINED_IN FROM LINK S1 INNER JOIN BOX S2 ON S1.ID = S2.CONTAINED_IN)\n" +
            "SELECT ID, CONTAINED_IN FROM LINK",
    nativeQuery = true)
    List<Box> getAllChildBoxesRecursive(Integer startBoxId);
}
