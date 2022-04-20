package ru.alexakaion.boxesapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ranges.RangeException;
import ru.alexakaion.boxesapp.model.Box;
import ru.alexakaion.boxesapp.model.Item;
import ru.alexakaion.boxesapp.repository.BoxRepository;
import ru.alexakaion.boxesapp.repository.ItemRepository;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CustomXmlReader implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CustomXmlReader.class);

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        List<Box> boxes = null;
        List<Item> items = null;

        if (args.length == 0 || args[0] == null) {
            throw new ExceptionInInitializerError("No Parameter with filename in command string");
        }

        File xmlFile = null;
        try {
            xmlFile = ResourceUtils.getFile(args[0]);
        } catch (Exception e) {
            LOG.error("Can't open file from path: "+args[0]);
            System.exit(0);
        }

        LOG.info("---Start XML reader---");
        LOG.info("filename=" + args[0]);

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            document.getDocumentElement().normalize();

            boxes = this.readEntities(document.getElementsByTagName("Box"),Box.class);
            items = this.readEntities(document.getElementsByTagName("Item"),Item.class);

        } catch (Exception e) {
            LOG.error("Can't parse XML File");
            e.printStackTrace();
            System.exit(0);
        }

        LOG.info("---Finish XML reader---");

        boxRepository.saveAll(boxes);
        itemRepository.saveAll(items);

        this.makeBackupH2();
    }

    private <T> List<T> readEntities(NodeList nodeList, Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<T> entity = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Integer id = null;
                try {
                    id = Integer.parseInt(Objects.requireNonNull(getAttributeByName(node, "id")));
                } catch (NullPointerException e) {
                    LOG.error("One of " + tClass.toString().substring(tClass.toString().lastIndexOf(".") + 1) + " doesn't have id in XML!");
                    System.exit(0);
                }

                Integer parentId = parseIntOrNull(getAttributeByName(node.getParentNode(), "id"));

                if (tClass == Box.class) {
                    entity.add((T) tClass.getDeclaredConstructor(Integer.class, Integer.class).newInstance(id, parentId));
                } else if (tClass == Item.class) {
                    String color = getAttributeByName(node, "color");
                    entity.add((T) tClass.getDeclaredConstructor(Integer.class, Integer.class, String.class).newInstance(id, parentId, color));
                } else {
                    throw new ClassNotFoundException("Not supported data class");
                }
            }
        }
        return entity;
    }

    private Integer parseIntOrNull(String str) {
        return str == null ? null : Integer.parseInt(str);
    }

    private String getAttributeByName(Node node, String attributeId) {
        Node attribute = node.getAttributes().getNamedItem(attributeId);
        return attribute == null ? null : attribute.getTextContent();
    }

    private void makeBackupH2() {
        //сохраняем бекап в файл для проверки
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("SCRIPT TO 'backup.sql'");
        } catch (Exception e) {
            LOG.error("Can't make backup!");
            e.printStackTrace();
        }
    }
}
