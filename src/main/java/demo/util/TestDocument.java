package demo.util;

import demo.model.Document;
import demo.model.Field;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TestDocument {

    public static void main(String[] args){

        List<Field> fieldsList = new ArrayList<Field>();
        //String fileName = "testing-13bf9f4d-d976-4b66-a49f-360e860e02b1.xml";
        String fileName = "data.xml";

        StaXParser read = new StaXParser();
        Document readDoc = read.readXml(fileName);
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
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            //Print XML String to Console
            jaxbMarshaller.marshal(document, new File("transformed-"+fileName));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
