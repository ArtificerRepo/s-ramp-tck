/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.18 at 03:02:22 PM EST 
//


package org.oasis_open.docs.s_ramp.ns.s_ramp_v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Service complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Service">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0}Element">
 *       &lt;sequence>
 *         &lt;element name="hasContract" type="{http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0}serviceContractTarget" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hasInterface" type="{http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0}serviceInterfaceTarget" maxOccurs="unbounded"/>
 *         &lt;element name="hasInstance" type="{http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0}serviceInstanceTarget" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Service", propOrder = {
    "hasContract",
    "hasInterface",
    "hasInstance"
})
public class Service
    extends Element
    implements Serializable
{

    private static final long serialVersionUID = 498461167799850877L;
    protected List<ServiceContractTarget> hasContract;
    @XmlElement(required = true)
    protected List<ServiceInterfaceTarget> hasInterface;
    protected ServiceInstanceTarget hasInstance;

    /**
     * Gets the value of the hasContract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hasContract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHasContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceContractTarget }
     *
     *
     */
    public List<ServiceContractTarget> getHasContract() {
        if (hasContract == null) {
            hasContract = new ArrayList<ServiceContractTarget>();
        }
        return this.hasContract;
    }

    /**
     * Gets the value of the hasInterface property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hasInterface property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHasInterface().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceInterfaceTarget }
     *
     *
     */
    public List<ServiceInterfaceTarget> getHasInterface() {
        if (hasInterface == null) {
            hasInterface = new ArrayList<ServiceInterfaceTarget>();
        }
        return this.hasInterface;
    }

    /**
     * Gets the value of the hasInstance property.
     *
     * @return
     *     possible object is
     *     {@link ServiceInstanceTarget }
     *
     */
    public ServiceInstanceTarget getHasInstance() {
        return hasInstance;
    }

    /**
     * Sets the value of the hasInstance property.
     *
     * @param value
     *     allowed object is
     *     {@link ServiceInstanceTarget }
     *     
     */
    public void setHasInstance(ServiceInstanceTarget value) {
        this.hasInstance = value;
    }

}
