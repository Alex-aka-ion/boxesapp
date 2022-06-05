package ru.alexakaion.boxesapp.model;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BoxSearchRequest {
    @NotNull
    private Integer box;
    @NotNull
    private String color;
}
