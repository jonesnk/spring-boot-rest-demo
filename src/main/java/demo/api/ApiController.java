package demo.api;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import demo.model.Document;
import demo.model.Field;
import demo.util.StaXParser;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiController {

    private String fileName = "document-"+UUID.randomUUID()+".xml";

    @RequestMapping(value = "/document", method = RequestMethod.POST, consumes="application/xml", produces = "application/vnd.fdf")
    @ResponseBody
    public String home(@RequestBody byte[] requestBody) throws Exception {

        //String fileName = "document-"+UUID.randomUUID()+".xml";
        //Path path = Paths.get("/Users/bgi056/IdeaProjects/spring-boot-rest-demo/src/main/resources/files/"+fileName);
        Path path = Paths.get("/Users/bgi056/IdeaProjects/spring-boot-rest-demo/"+fileName);
        //Path path = Paths.get(System.getProperty("user.home")+fileName);
        //Path path = Paths.get("/home/ec2-user/"+fileName);

        try {
            System.out.println("Trying to reach the filesystem");
            //written file to path
            Path writtenPath = Files.write(path, requestBody);
            System.out.println("Successfully created file.");
            //uploadToS3 (fileName, writtenPath.toFile());
            //transformFile(fileName);
            transformPayload(fileName, requestBody);
            System.out.println("Written content in file:\n"+ new String(Files.readAllBytes(writtenPath)));
            // add aws code
            return bodyMessage("Thank you and good bye.");
        } catch (Exception e) {
            e.printStackTrace();
            return bodyMessage("Something is wrong.");
        }

    }

    private static void transformFile(String fileName){

        List<Field> fieldsList = new ArrayList<Field>();

        StaXParser parser = new StaXParser();
        Document readDoc = parser.readXmlFile(fileName);
        for (Field field : readDoc.getFields()) {
            fieldsList.add(field);
        }

        Document document = new Document();
        document.setIds(readDoc.getIds());
        document.setFields(fieldsList);

        writeObjectToXML(document, fileName);
    }

    private static void transformPayload(String fileName, byte[] requestBody){

        List<Field> fieldsList = new ArrayList<Field>();
        StaXParser parser = new StaXParser();

        Document readDoc = parser.readBytes(requestBody);

        for (Field field : readDoc.getFields()) {
            fieldsList.add(field);
        }

        Document document = new Document();
        document.setIds(readDoc.getIds());
        document.setFields(fieldsList);

        writeObjectToXML(document, fileName);
    }

    private static void writeObjectToXML(Document document, String fileName)

    {
        String newFile = "transformed-"+fileName;
        File file = new File(newFile);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(document, file);

            //uploadToS3(newFile, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void uploadToS3 (String keyName, File file) throws AmazonServiceException {
      //  AWSCredentials credentials = null;
        String bucketName = "njones-gdit";

/*        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }*/

        System.out.println("Trying to reach Amazon S3");

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                //.withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion("us-east-1")
                .build();

        System.out.println("Connected to S3");

        try {

            //ByteArrayInputStream    contentsAsStream      = new ByteArrayInputStream(requestBody);
            ObjectMetadata          md = new ObjectMetadata();
            md.setContentType("application/xml");
            //md.setContentLength(requestBody.length);
            //s3.putObject(new PutObjectRequest(bucketName, keyName, file, md));
            s3.putObject(new PutObjectRequest(bucketName, keyName, file));
            System.out.println("Successfully uploaded file to S3");


            // Copy the object into a new object in the same bucket.
            CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, keyName, bucketName+"/copied", keyName);
            s3.copyObject(copyObjRequest);
            System.out.println("Successfully copied file to S3 folder");

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    private static String bodyMessage (String message) {

        String body = "%FDF-1.2\r\n" +
                "1 0 obj\r\n" +
                "<</FDF <</Status ("+message+") >> >>\r\n" +
                "endobj\r\n" +
                "trailer\r\n" +
                "<</Root 1 0 R>>\r\n" +
                "%%EOF";

        return body;
    }


}
