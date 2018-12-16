package demo.model;

import com.sun.xml.internal.txw2.annotation.XmlCDATA;
import demo.util.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.UUID;

@XmlRootElement(name="field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @XmlAttribute
    private String name;

   @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String value;

    @XmlElement(name="value-richtext")
    //@XmlJavaTypeAdapter(AdapterCDATA.class)
    private String valueRichtext;

    public Field(){
    }

    public Field(String name, String value, String valueRichtext) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.value = value;
        this.valueRichtext = valueRichtext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueRichtext() { return valueRichtext; }

    public void setValueRichtext(String valueRichtext) { this.valueRichtext = valueRichtext; }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", valueRichtext='" + valueRichtext + '\'' +
                '}';
    }
}
