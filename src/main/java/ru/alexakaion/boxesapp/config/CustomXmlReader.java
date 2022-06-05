package ru.alexakaion.boxesapp.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ru.alexakaion.boxesapp.model.*;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.H2BackupRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomXmlReader implements CommandLineRunner {
    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;
    private final H2BackupRepository h2BackupRepository;

    @Override
    public void run(String... args) throws FileNotFoundException {
        if (args.length == 0) {
            throw new IllegalArgumentException("No Parameter with filename in command string");
        }
        log.debug("filename=" + args[0]);

        File xmlFile = ResourceUtils.getFile(args[0]);

        XmlStorage storage = this.readXml(xmlFile);

        this.saveToDatabase(storage);

        h2BackupRepository.makeH2Backup("backup.sql");
    }

    private void saveToDatabase(XmlStorage storage) {
        this.saveXmlBoxesAndXmlItems(storage.getXmlBoxes(), null);
        storage.getXmlItems().forEach(xmlItem -> this.saveXmlItem(xmlItem, null));
    }

    private XmlStorage readXml(File xmlFile) {
        log.debug("---Start XML reader---");

        XmlStorage storage;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlStorage.class, XmlBox.class, XmlItem.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            storage = (XmlStorage) unmarshaller.unmarshal(xmlFile);

            log.debug(storage.toString());
        } catch (Exception e) {
            log.error("Can't parse XML File!",e);
            throw new RuntimeException(e);
        }

        log.debug("---Finish XML reader---");
        return storage;
    }
    private void saveXmlBoxesAndXmlItems(List<XmlBox> xmlBoxes, Integer parentId) {
        xmlBoxes.forEach(xmlBox -> {
            this.saveXmlBox(xmlBox, parentId);

            xmlBox.getXmlItems().forEach(xmlItem -> this.saveXmlItem(xmlItem, xmlBox.getId()));

            this.saveXmlBoxesAndXmlItems(xmlBox.getXmlBoxes(), xmlBox.getId());
        });
    }

    private void saveXmlItem(XmlItem xmlItem, Integer parentId) {
        Item item = new Item(xmlItem.getId(), parentId, xmlItem.getColor());
        itemRepository.save(item);
    }

    private void saveXmlBox(XmlBox xmlBox, Integer parentId) {
        Box box = new Box(xmlBox.getId(), parentId);
        boxRepository.save(box);
    }
}
