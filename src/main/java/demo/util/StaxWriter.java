package demo.util;

import demo.model.Field;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by bgi056 on 12/10/18.
 */
public class StaxWriter {

    private String xmlFile;

    public void setFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }


    public void saveConfig() throws Exception {
        // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(xmlFile));
        // create an EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);



        StaXParser read = new StaXParser();

/*        List<Field> readConfig = read.readConfig("testing-13bf9f4d-d976-4b66-a49f-360e860e02b1.xml");
        for (Field field : readConfig) {

            // create config open tag
            StartElement configStartElement = eventFactory.createStartElement("",
                    "", "field");
            eventWriter.add(configStartElement);
            eventWriter.add(end);
            // Write the different nodes
            createNode(eventWriter, "name", field.getName());
            createNode(eventWriter, "value", field.getValue());

            eventWriter.add(eventFactory.createEndElement("", "", "field"));
            eventWriter.add(end);

        }*/



        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }



    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

}
