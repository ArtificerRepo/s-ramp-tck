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
package org.oasis_open.s_ramp.tck.foundation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Property;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ServiceInstance;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.WsdlDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XsdDocumentTarget;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class Test_4_2 extends AbstractFoundationTest {
    
    public Test_4_2(Binding binding) {
        super(binding);
    }
    
    @Test
    public void test_examples() throws Exception {
        // Create with *and* without the name to verify the query works as expected.
        BaseArtifactType artifact = XsdDocument();
        BaseArtifactType uploadedXsd = binding.upload(artifact, "/PO.xsd");
        verifyArtifact(uploadedXsd);
        artifact.setName("bob");
        verifyArtifact(binding.upload(artifact, "/PO.xsd"));
        
        artifact = XsdDocument();
        Property property = new Property();
        property.setPropertyName("someProperty");
        property.setPropertyValue("high");
        artifact.getProperty().add(property);
        verifyArtifact(binding.upload(artifact, "/PO.xsd"));
        
        // Create with *and* without the property to verify the query works as expected.
        artifact = new ServiceInstance();
        verifyArtifact(binding.create(artifact));
        artifact = new ServiceInstance();
        property = new Property();
        property.setPropertyName("someProperty");
        property.setPropertyValue("high");
        artifact.getProperty().add(property);
        verifyArtifact(binding.create(artifact));

        // Create with *and* without the name to verify the query works as expected.
        artifact = ArtifactType.ExtendedArtifactType("BpmnDocument", false).newArtifactInstance();
        verifyArtifact(binding.create(artifact));
        artifact.setName("LoanApproval");
        verifyArtifact(binding.create(artifact));
        
        // Create with *and* without the property to verify the query works as expected.
        WsdlDocument wsdlArtifact = WsdlDocument();
        verifyArtifact(binding.upload(wsdlArtifact, "/deriver.wsdl"));
        XsdDocumentTarget xsdTarget = new XsdDocumentTarget();
        xsdTarget.setHref(binding.getUrl(uploadedXsd));
        xsdTarget.setValue("foo");
        wsdlArtifact.getIncludedXsds().add(xsdTarget);
        verifyArtifact(binding.upload(wsdlArtifact, "/deriver.wsdl"));
        
        // Example 3:  Query Expressions Using Properties
        List<BaseArtifactType> artifacts = binding.query("/s-ramp/xsd/XsdDocument[@someProperty]");
        assertEquals(1, artifacts.size());
        assertEquals(1, artifacts.get(0).getProperty().size());
        assertEquals("someProperty", artifacts.get(0).getProperty().get(0).getPropertyName());
        verifyArtifacts(artifacts);
        artifacts = binding.query("/s-ramp/xsd/XsdDocument[@name = 'bob']");
        assertEquals(1, artifacts.size());
        assertEquals("bob", artifacts.get(0).getName());
        verifyArtifacts(artifacts);
        artifacts = binding.query("/s-ramp/serviceImplementation/ServiceInstance[@someProperty = 'high']");
        assertEquals(1, artifacts.size());
        assertEquals(1, artifacts.get(0).getProperty().size());
        assertEquals("someProperty", artifacts.get(0).getProperty().get(0).getPropertyName());
        verifyArtifacts(artifacts);
        
        // Example 4:  Query Expression Using Relationships
        // TODO FAILURE: SRAMP-547
//        artifacts = binding.query("/s-ramp/wsdl/WsdlDocument[includedXsds]");
//        assertEquals(1, artifacts.size());
//        wsdlArtifact = (WsdlDocument) artifacts.get(0);
//        assertEquals(1, wsdlArtifact.getIncludedXsds().size());
        
        // Example 5:  Query Expression Using Relationships and Properties
        // TODO FAILURE: SRAMP-547
//        artifacts = binding.query("/s-ramp/wsdl/WsdlDocument[includedXsds[@someProperty='true']]");
//        assertEquals(1, artifacts.size());
//        wsdlArtifact = (WsdlDocument) artifacts.get(0);
//        assertEquals(1, wsdlArtifact.getIncludedXsds().size());
        
        // Example 6:  Extended Artifacts
        artifacts = binding.query("/s-ramp/ext/BpmnDocument[@name = 'LoanApproval']");
        assertEquals(1, artifacts.size());
        assertEquals("LoanApproval", artifacts.get(0).getName());
        verifyArtifacts(artifacts);
        
        // Example 7:  Query Expressions Using Relationships as Sub-Artifact Sets
        // TODO FAILURE: SRAMP-548
//        artifacts = binding.query("/s-ramp/wsdl/Message[@name='findRequest']/part");
//        assertEquals(1, artifacts.size());
//        verifyArtifacts(artifacts);
    }
}
