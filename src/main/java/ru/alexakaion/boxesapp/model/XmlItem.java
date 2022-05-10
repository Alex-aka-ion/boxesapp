package ru.alexakaion.boxesapp.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlItem {
    @XmlAttribute(name="id")
    private Integer id;

    @XmlAttribute(name="color")
    private String color;
}
