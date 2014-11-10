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

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Property;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Relationship;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class Test_2_2 extends AbstractFoundationTest {
    
    public Test_2_2(Binding binding) {
        super(binding);
    }
    
    @Test
    public void test_2_2_1_2() throws Exception {
        BaseArtifactType targetArtifact = XsdDocument();
        targetArtifact = binding.upload(targetArtifact, "/PO.xsd");
        verifyArtifact(targetArtifact);
        
        BaseArtifactType artifact = XsdDocument();
        Property property = new Property();
        property.setPropertyName("someProperty");
        property.setPropertyValue("high");
        artifact.getProperty().add(property);
        verifyArtifact(binding.upload(artifact, "/PO.xsd"));
        
        Relationship relationship = new Relationship();
        // Property names cannot duplicate relationship names.
        relationship.setRelationshipType("someProperty");
        artifact.getRelationship().add(relationship);
        binding.upload(artifact, "/PO.xsd", 409);
        
        artifact.getRelationship().clear();
        
        // Cannot duplicate property names.
        artifact.getProperty().add(property);
        binding.upload(artifact, "/PO.xsd", 409);
        
        artifact.getProperty().clear();
        
        // Cannot duplicate built-in property names.
        property.setPropertyName("description");
        artifact.getProperty().add(property);
        binding.upload(artifact, "/PO.xsd", 409);
        
        artifact.getProperty().clear();
        
        // Cannot duplicate built-in relationship names.
        property.setPropertyName("importedXsds");
        artifact.getProperty().add(property);
        binding.upload(artifact, "/PO.xsd", 409);
    }
    
    @Test
    public void test_2_2_1_3() throws Exception {
        BaseArtifactType artifact = XsdDocument();
        Relationship relationship = new Relationship();
        relationship.setRelationshipType("someRelationship");
        artifact.getRelationship().add(relationship);
        verifyArtifact(binding.upload(artifact, "/PO.xsd"));
        
        Property property = new Property();
        // Property names cannot duplicate relationship names.
        property.setPropertyName("someRelationship");
        property.setPropertyValue("foo");
        artifact.getProperty().add(property);
        binding.upload(artifact, "/PO.xsd", 409);
        
        artifact.getProperty().clear();
        
        // Cannot duplicate built-in property names.
        relationship.setRelationshipType("description");
        artifact.getRelationship().add(relationship);
        binding.upload(artifact, "/PO.xsd", 409);
        
        artifact.getRelationship().clear();
        
        // Cannot duplicate built-in relationship names.
        relationship.setRelationshipType("importedXsds");
        artifact.getRelationship().add(relationship);
        binding.upload(artifact, "/PO.xsd", 409);
    }
    
    // TODO: 2.2.2 Documents which have a Derived Model associated with them cannot be updated in the repository.  They must be removed and republished.
    // TODO: 2.2.2 Documents which are the target of a relationship cannot be deleted.
}
