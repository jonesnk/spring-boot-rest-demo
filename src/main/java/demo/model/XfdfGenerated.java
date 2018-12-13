package demo.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "fields",
        "ids"
})
@XmlRootElement(name = "xfdf")
public class XfdfGenerated {

    protected XfdfGenerated.Fields fields;

    @XmlElement(required = true)
    protected XfdfGenerated.Ids ids;

    public XfdfGenerated.Fields getFields() {
        return fields;
    }

    public void setFields(XfdfGenerated.Fields value) {
        this.fields = value;
    }

    public XfdfGenerated.Ids getIds() {
        return ids;
    }

    public void setIds(XfdfGenerated.Ids value) {
        this.ids = value;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "field"
    })
    public static class Fields {

        @XmlElement(required = true)
        protected List<Field> field;

        @XmlAttribute(required = true)
        protected String name;

        public List<XfdfGenerated.Fields.Field> getField() {
            if (field == null) {
                field = new ArrayList<Field>();
            }
            return this.field;
        }

        public String getName() {
            return name;
        }

        public void setName(String value) {
            this.name = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value",
                "valueRichtext"
        })
        public static class Field {

            protected String value;
            @XmlElement(name = "value-richtext")
            protected String valueRichtext;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getValueRichtext() {
                return valueRichtext;
            }

            public void setValueRichtext(String value) {
                this.valueRichtext = value;
            }

        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Ids {

        @XmlAttribute(required = true)
        protected String original;
        @XmlAttribute(required = true)
        protected String modified;

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String value) {
            this.original = value;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String value) {
            this.modified = value;
        }

    }

}