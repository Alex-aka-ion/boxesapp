package ru.alexakaion.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.alexakaion.boxesapp.model.BoxSearchRequest;
import ru.alexakaion.boxesapp.service.BoxSearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final BoxSearchService boxSearchService;

    @PostMapping("/test")
    public List<Integer> getBoxIdsWithColor(@RequestBody BoxSearchRequest request) {
        return boxSearchService.searchBoxByColorInsideBoxes(
                request.getBox(),
                request.getColor()
        );
    }
}
