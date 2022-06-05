package ru.alexakaion.boxesapp.model;

import lombok.*;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name="Box")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlBox {
    @XmlElement(name="Box")
    private List<XmlBox> xmlBoxes = new ArrayList<>();

    @XmlElement(name="Item")
    private List<XmlItem> xmlItems = new ArrayList<>();

    @XmlAttribute(name="id")
    private Integer id;
}
