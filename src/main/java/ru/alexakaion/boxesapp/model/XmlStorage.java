package ru.alexakaion.boxesapp.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="Storage")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlStorage {
    @XmlElement(name="Box")
    private final List<XmlBox> xmlBoxes = new ArrayList<>();
    @XmlElement(name="Item")
    private final List<XmlItem> xmlItems = new ArrayList<>();
}
