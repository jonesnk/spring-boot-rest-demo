package demo.util;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by bgi056 on 12/16/18.
 */
public class StaxTest {

    public static void main (String[] args){

        try {
            //File Path
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<xfdf xmlns=\"http://ns.adobe.com/xfdf/\" xml:space=\"preserve\"" +
                    "><fields" +
                    "><field name=\"SECTION_01_111_A_01_TXT\"" +
                    "><value-richtext" +
                    "><p>Nicole Jones</p></value-richtext" +
                    "></field" +
                    "><field name=\"SECTION_01_111_B_01_TXT\"" +
                    "><value-richtext" +
                    "><p>Nicole Jones</p></value-richtext" +
                    "></field" +
                    "><field name=\"SECTION_01_111_C_01_TXT\"" +
                    "><value" +
                    ">Nicole Joness</value" +
                    "></field" +
                    "></fields><ids original=\"A0015F3753563E09C8F835792CB0E07A\" modified=\"8CA695C4D3FE024AA1F742B73253F350\"" +
                    "/></xfdf" +
                    ">";

            //String to bytes
            byte[] myBytes = xml.getBytes();

            ByteArrayInputStream in = new ByteArrayInputStream(myBytes);
            if (in == null)
                    throw new IOException("Failed to get resource");

                DocumentReader reader = new DocumentReader();
                System.out.println(reader.readFromXML(in));
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*ByteArrayInputStream in = new ByteArrayInputStream(myBytes);
            XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(in);

            //Iterate through events.
            while(xmlStreamReader.hasNext()){
                //Get integer value of current event.
                int xmlEvent = xmlStreamReader.next();

                //Process start element.
                if (xmlEvent == XMLStreamConstants.START_ELEMENT) {
                    System.out.println("Start Element: "
                            +xmlStreamReader.getLocalName());

                    if(xmlStreamReader.getName().getLocalPart().equals("value-richtext")){
                        xmlStreamReader.next();

                        System.out.println("Inside the next element: "
                                +xmlStreamReader.getElementText());

                    }
                    int attributes =
                            xmlStreamReader.getAttributeCount();
                    for(int i=0; i<attributes; i++){
                        QName name = xmlStreamReader.getAttributeName(i);
                        String value=xmlStreamReader.getAttributeValue(i);
                        System.out.println("Attribute name: " + name);
                        System.out.println("Attribute value: " + value);
                    }
                }

                //Process end element.
                if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
                    System.out.println("End Element: "
                            +xmlStreamReader.getLocalName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        }
}
