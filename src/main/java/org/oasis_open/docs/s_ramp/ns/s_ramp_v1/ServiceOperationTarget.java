//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.08.01 at 01:38:08 PM EDT 
//


package org.oasis_open.docs.s_ramp.ns.s_ramp_v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceOperationTarget complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceOperationTarget">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0>target">
 *       &lt;attribute name="artifactType" use="required" type="{http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0}serviceOperationEnum" />
 *       &lt;anyAttribute/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceOperationTarget")
public class ServiceOperationTarget
    extends Target
{

    @XmlAttribute(name = "artifactType", required = true)
    protected ServiceOperationEnum artifactType;

    /**
     * Gets the value of the artifactType property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceOperationEnum }
     *     
     */
    public ServiceOperationEnum getArtifactType() {
        return artifactType;
    }

    /**
     * Sets the value of the artifactType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceOperationEnum }
     *     
     */
    public void setArtifactType(ServiceOperationEnum value) {
        this.artifactType = value;
    }

}
