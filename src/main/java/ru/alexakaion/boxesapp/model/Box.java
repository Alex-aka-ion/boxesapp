package ru.alexakaion.boxesapp.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "box")
public class Box {
    @Id
    @NonNull
    private Integer id;

    private Integer containedIn;
}
