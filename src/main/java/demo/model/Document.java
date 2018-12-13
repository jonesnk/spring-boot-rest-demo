package demo.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * <fields>
 *     <field name="TXT_NAME">
 *         <value>this is a test</value>
 *    </field>
 * </fields>
 */

@XmlRootElement(name = "xfdf")
@XmlAccessorType(XmlAccessType.FIELD)
public class Document implements Serializable {

    @XmlElementWrapper(name="fields")
    @XmlElement(name="field")
    private List<Field> fields;

    private Ids ids;

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
