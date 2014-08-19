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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.DocumentArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Property;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Relationship;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public abstract class CoreModelTest extends AbstractTest {

    public CoreModelTest(Binding binding) {
        super(binding);
    }
    
    protected void verifyArtifacts(List<BaseArtifactType> artifacts, Class<? extends BaseArtifactType> expectedClass) {
        assertTrue(artifacts.size() > 0);
        for (BaseArtifactType artifact : artifacts) {
            verifyArtifact(artifact, expectedClass);
        }
    }

    protected void verifyArtifacts(List<BaseArtifactType> artifacts) {
        assertTrue(artifacts.size() > 0);
        for (BaseArtifactType artifact : artifacts) {
            verifyArtifact(artifact);
        }
    }
    
    protected void verifyArtifact(BaseArtifactType artifact, Class<? extends BaseArtifactType> expectedClass) {
        assertEquals(expectedClass, artifact.getClass());
        verifyArtifact(artifact);
    }
    
    protected void verifyArtifact(BaseArtifactType artifact) {
        // Foundation 2.2.1.1
        assertNotNull(artifact.getArtifactType());
        stringAssertion(artifact.getCreatedBy());
        assertNotNull(artifact.getArtifactType());
        timeAssertion(artifact.getCreatedTimestamp());
        stringAssertion(artifact.getLastModifiedBy());
        timeAssertion(artifact.getLastModifiedTimestamp());
        stringAssertion(artifact.getName());
        stringAssertion(artifact.getUuid());
        
        // Foundation 2.2.1.2, 2.2.1.3
        List<String> names = new ArrayList<String>();
        for (Relationship relationship : artifact.getRelationship()) {
            assertFalse(names.contains(relationship.getRelationshipType()));
            names.add(relationship.getRelationshipType());
        }
        for (Property property : artifact.getProperty()) {
            assertFalse(names.contains(property.getPropertyName()));
            names.add(property.getPropertyName());
        }
        
        // Foundation 2.2.2
        if (artifact instanceof DocumentArtifactType) {
            DocumentArtifactType document = (DocumentArtifactType) artifact;
            stringAssertion(document.getContentType());
            longAssertion(document.getContentSize());
            stringAssertion(document.getContentHash());
        }
    }
    
    private void stringAssertion(String s) {
        assertNotNull(s);
        assertTrue(s.length() > 0);
    }
    
    private void timeAssertion(XMLGregorianCalendar time) {
        assertNotNull(time);
        assertTrue(time.getYear() > 1970);
    }
    
    private void longAssertion(Long n) {
        assertNotNull(n);
        assertTrue(n > 0);
    }

}
