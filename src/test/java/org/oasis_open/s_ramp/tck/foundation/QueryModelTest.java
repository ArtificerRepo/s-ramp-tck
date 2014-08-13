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
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactEnum;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class QueryModelTest extends AbstractTest {
    
    public QueryModelTest(Binding binding) {
        super(binding);
    }
    
    @Test
    public void test_foundation_4_2() throws Exception {
        // setup
        createdArtifacts.add(binding.upload(ArtifactType.XsdDocument(), "/PO.xsd"));
        
        BaseArtifactType artifact = ArtifactType.XsdDocument().newArtifactInstance();
        artifact.setArtifactType(BaseArtifactEnum.XSD_DOCUMENT);
        artifact.setName("bob"); //$NON-NLS-1$
        createdArtifacts.add(binding.upload(artifact, "/PO.xsd"));
        
        // Example 3:  Query Expressions Using Properties
//        verifyArtifacts(binding.query("/s-ramp/xsd/XsdDocument[@someProperty]"));
        List<BaseArtifactType> artifacts = binding.query("/s-ramp/xsd/XsdDocument[@name = 'bob']");
        assertEquals(1, artifacts.size());
        assertEquals("bob", artifacts.get(0).getName());
        verifyArtifacts(artifacts);
//        verifyArtifacts(binding.query("/s-ramp/serviceImplementation/ServiceInstance[@someProperty ='high']"));
        
        // Example 4:  Query Expression Using Relationships
//        verifyArtifacts(binding.query("/s-ramp/wsdl/WsdlDocument[includedXsds]"));
        
        // Example 5:  Query Expressions Using Relationships and Properties
//        verifyArtifacts(binding.query("/s-ramp/ext/BpmnDocument[@name = 'LoanApproval']"));
        
        // Example 6:  Extended Artifacts
//        verifyArtifacts(binding.query("/s-ramp/wsdl/Message[@name='PurchaseRequestMessage']/part"));
        
        // Example 7:  Query Expressions Using Relationships as Sub-Artifact Sets
//        verifyArtifacts(binding.query("/s-ramp/xsd/XsdDocument[@someProperty]"));
    }
    
    private void verifyArtifacts(List<BaseArtifactType> artifacts) {
        for (BaseArtifactType artifact : artifacts) {
            verifyArtifact(artifact);
        }
    }
    
    private void verifyArtifact(BaseArtifactType artifact) {
        // TODO
    }
}
