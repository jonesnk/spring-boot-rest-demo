package demo.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.UUID;

@XmlRootElement(name="field")
@XmlAccessorType(XmlAccessType.FIELD)
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @XmlAttribute
    private String name;

    private String value;

    @XmlElement(name="value-richtext")
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
