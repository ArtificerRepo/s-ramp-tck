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
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.*;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void test_2_2_2() throws Exception {
        Task task = new Task();
        task = (Task) binding.create(task);
        verifyArtifact(task);
        TaskTarget taskTarget = new TaskTarget();
        taskTarget.setArtifactType(TaskEnum.TASK);
        taskTarget.setValue(task.getUuid());
        Actor actor = new Actor();
        actor.getDoes().add(taskTarget);
        actor = (Actor) binding.create(actor);
        verifyArtifact(actor);

        // Cannot delete an artifact that's targeted by a relationship
        binding.delete(task, 409);

        // Delete the relationship and retry
        actor.getDoes().clear();
        binding.update(actor);
        binding.delete(task);
        binding.get(task.getUuid(), ArtifactType.valueOf(task), 404);
    }
}
