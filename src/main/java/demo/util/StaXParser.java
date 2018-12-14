package demo.util;

import demo.model.Document;
import demo.model.Field;
import demo.model.Ids;

import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class StaXParser {

    static final String IDS = "ids";
    static final String ORIGINAL = "original";
    static final String MODIFIED = "modified";


    static final String FIELD = "field";
    static final String ID = "id";
    static final String VALUE = "value";
    static final String VALUE_RICH_TEXT = "value-richtext";
    static final String NAME = "name";


    @SuppressWarnings({ "unchecked", "null" })
    public Document readXml(String xmlFile) {
        List<Field> fields = new ArrayList<Field>();
        Document document = null;

        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(xmlFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Field field = null;
            Ids ids = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have a document element, we create a new document id
                    if (startElement.getName().getLocalPart().equals(IDS)) {
                        document = new Document();
                        ids = new Ids();
                        // We read the attributes from this tag and add the name
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(ORIGINAL)) {
                                ids.setOriginal(attribute.getValue());
                            }

                            if (attribute.getName().toString().equals(MODIFIED)) {
                                ids.setModified(attribute.getValue());
                            }
                        }
                        document.setIds(ids);
                    }
                    // If we have a field element, we create a new field
                    if (startElement.getName().getLocalPart().equals(FIELD)) {
                        field = new Field();
                        // We read the attributes from this tag and add the name
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(NAME)) {
                                field.setName(attribute.getValue());
                            }

                        }
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(ID)) {
                        event = eventReader.nextEvent();
                        field.setId(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(VALUE)) {
                        event = eventReader.nextEvent();
                        field.setValue(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(VALUE_RICH_TEXT)) {
                        event = eventReader.nextEvent();
                        field.setValueRichtext(event.asCharacters().getData());
                        continue;
                    }

                }
                // If we reach the end of an field element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(FIELD)) {
                        fields.add(field);
                    }
                }

            }
            document.setFields(fields);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return document;
    }

}
