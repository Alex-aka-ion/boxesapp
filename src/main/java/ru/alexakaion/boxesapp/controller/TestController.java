package ru.alexakaion.boxesapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.alexakaion.boxesapp.service.BoxSearcher;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private BoxSearcher boxSearcher;

    @PostMapping("/test")
    public List<Integer> getBoxIdsWithColor(@RequestBody Map<String, Object> payload) {
        return boxSearcher.searchBoxByColorInsideBoxes(Integer.parseInt(payload.get("box").toString()), payload.get("color").toString());
    }
}
