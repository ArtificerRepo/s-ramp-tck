//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.01 at 01:38:08 PM EDT 
//


package org.oasis_open.docs.s_ramp.ns.s_ramp_v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for taskEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="taskEnum">
 *   &lt;restriction base="{http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0}baseArtifactEnum">
 *     &lt;enumeration value="Task"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "taskEnum")
@XmlEnum(BaseArtifactEnum.class)
public enum TaskEnum {

    @XmlEnumValue("Task")
    TASK(BaseArtifactEnum.TASK);
    private final BaseArtifactEnum value;

    TaskEnum(BaseArtifactEnum v) {
        value = v;
    }

    public BaseArtifactEnum value() {
        return value;
    }

    public static TaskEnum fromValue(BaseArtifactEnum v) {
        for (TaskEnum c: TaskEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
