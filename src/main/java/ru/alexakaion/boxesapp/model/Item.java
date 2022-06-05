package ru.alexakaion.boxesapp.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @NonNull
    private Integer id;

    private Integer containedIn;

    @Column(length = 100)
    private String color;
}
