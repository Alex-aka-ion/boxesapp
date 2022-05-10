package ru.alexakaion.boxesapp.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.alexakaion.boxesapp.model.*;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.CommonRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomXmlReader implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CustomXmlReader.class);

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;
    private final CommonRepository commonRepository;

    @Override
    public void run(String... args) {
        List<Box> boxes = new ArrayList<>();
        List<Item> items = new ArrayList<>();

        if (args.length == 0) {
            throw new IllegalArgumentException("No Parameter with filename in command string");
        }

        File xmlFile = null;
        try {
            xmlFile = ResourceUtils.getFile(args[0]);
        } catch (Exception e) {
            LOG.error("Can't open file from path: "+args[0], e);
            System.exit(0);
        }

        LOG.info("---Start XML reader---");
        LOG.info("filename=" + args[0]);

        XmlStorage storage = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlStorage.class, XmlBox.class, XmlItem.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            storage = (XmlStorage) unmarshaller.unmarshal(xmlFile);

            LOG.info(storage.toString());

        } catch (Exception e) {
            LOG.error("Can't parse XML File",e);
            System.exit(0);
        }

        LOG.info("---Finish XML reader---");

        this.boxItemSaver(storage.getXmlBoxes(), null);
        storage.getXmlItems().forEach(xmlItem -> this.itemSaver(xmlItem, null));

        commonRepository.makeH2Backup("backup.sql");
    }

    private void boxItemSaver(List<XmlBox> xmlBoxes, Integer parentId) {
        xmlBoxes.forEach(xmlBox -> {
            this.boxSaver(xmlBox, parentId);

            xmlBox.getXmlItems().forEach(xmlItem -> this.itemSaver(xmlItem, xmlBox.getId()));

            this.boxItemSaver(xmlBox.getXmlBoxes(), xmlBox.getId());
        });
    }

    private void itemSaver(XmlItem xmlItem, Integer parentId) {
        Item item = new Item(xmlItem.getId(), parentId, xmlItem.getColor());
        itemRepository.save(item);
    }

    private void boxSaver(XmlBox xmlBox, Integer parentId) {
        Box box = new Box(xmlBox.getId(), parentId);
        boxRepository.save(box);
    }
}
