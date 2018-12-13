package demo.util;

/**
 * Created by bgi056 on 12/10/18.
 */
public class TestWrite {

    public static void main(String[] args) {
        StaxWriter xmlFile = new StaxWriter();
        xmlFile.setFile("testing.xml");
        try {
           xmlFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
