package demo.util;

import javax.xml.stream.*;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.util.Calendar;

/**
 * Created by bgi056 on 12/11/18.
 */
public class StaxEvent {

    XMLEventFactory m_eventFactory = XMLEventFactory.newInstance();

    public static void main(String[] args) throws Exception {
        StaxEvent ms = new StaxEvent();

        XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new java.io.FileInputStream("testing-13bf9f4d-d976-4b66-a49f-360e860e02b1.xml"));
        XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(System.out);

        while (reader.hasNext()) {
            XMLEvent event = (XMLEvent) reader.next();

            if (event.getEventType() == event.CHARACTERS) {
                writer.add(ms.getNewCharactersEvent(event.asCharacters()));
            } else {
                writer.add(event);
            }
        }
        writer.flush();
    }

    private Characters getNewCharactersEvent(Characters event) {
        if (event.getData().equalsIgnoreCase("Name1")) {
            return m_eventFactory.createCharacters(Calendar.getInstance().getTime().toString());
        } else {
            return event;
        }
    }
}
