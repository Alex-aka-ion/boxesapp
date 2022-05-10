package ru.alexakaion.boxesapp.model;

import lombok.*;
import org.springframework.data.annotation.Transient;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "box")
public class Box {
    @Id
    @NonNull
    @XmlAttribute(name="id")
    private Integer id;

    private Integer containedIn;
}
