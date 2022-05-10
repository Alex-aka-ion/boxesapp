package ru.alexakaion.boxesapp.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BoxSearcherRequest {
    @NotNull
    private String box;
    @NotNull
    private String color;
}
