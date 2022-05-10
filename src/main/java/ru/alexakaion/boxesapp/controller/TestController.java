package ru.alexakaion.boxesapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.alexakaion.boxesapp.model.BoxSearcherRequest;
import ru.alexakaion.boxesapp.service.BoxSearcherService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final BoxSearcherService boxSearcherService;

    @PostMapping("/test")
    public List<Integer> getBoxIdsWithColor(@RequestBody BoxSearcherRequest request) {
        return boxSearcherService.searchBoxByColorInsideBoxes(
                Integer.parseInt(request.getBox()),
                request.getColor()
        );
    }
}
