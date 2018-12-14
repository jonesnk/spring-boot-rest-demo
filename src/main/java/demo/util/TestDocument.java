package demo.util;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
import demo.model.Document;
import demo.model.Field;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class TestDocument {

    public static void main(String[] args){

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<xfdf xmlns=\"http://ns.adobe.com/xfdf/\" xml:space=\"preserve\"" +
                "><fields" +
                "><field name=\"SECTION_01_111_A_01_TXT\"" +
                "><value" +
                "><p>Nicole Jones</p></value" +
                "></field" +
                "><field name=\"SECTION_01_111_B_01_TXT\"" +
                "><value" +
                "><p>Nicole Jones</p></value" +
                "></field" +
                "><field name=\"SECTION_01_111_C_01_TXT\"" +
                "><value" +
                "><p>Nicole Jones</p></value" +
                "></field" +
                "></fields><ids original=\"A0015F3753563E09C8F835792CB0E07A\" modified=\"8CA695C4D3FE024AA1F742B73253F350\"" +
                "/></xfdf" +
                ">";

        List<Field> fieldsList = new ArrayList<Field>();
        //String fileName = "testing-13bf9f4d-d976-4b66-a49f-360e860e02b1.xml";
        String fileName = "data.xml";


        byte[] myBytes = xml.getBytes();

        StaXParser read = new StaXParser();
        Document readDoc = read.readBytes(myBytes);
        for (Field field : readDoc.getFields()) {
            //System.out.println(field);
            fieldsList.add(field);
        }

        Document document = new Document();
        document.setIds(readDoc.getIds());
        document.setFields(fieldsList);

        jaxbObjectToXML(document, fileName);
    }


    private static void jaxbObjectToXML(Document document, String fileName)
    {

        //Unmarshalling: Converting XML content to Java objects

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            jaxbMarshaller.setProperty(CharacterEscapeHandler.class.getName(),
                    new CharacterEscapeHandler() {
                        @Override
                        public void escape(char[] ac, int i, int j, boolean flag,
                                           Writer writer) throws IOException {
                            writer.write(ac, i, j);
                        }
                    });

            //Marshalling: Writing Java object to XML file
            //jaxbMarshaller.marshal(document, new File("transformed-"+fileName));
            jaxbMarshaller.marshal(document, new OutputStreamWriter(System.out)
            );

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
