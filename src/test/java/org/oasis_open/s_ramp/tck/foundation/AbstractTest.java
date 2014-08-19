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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.DocumentArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Property;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Relationship;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.WsdlDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XsdDocument;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;
import org.oasis_open.s_ramp.tck.BindingFactory;

/**
 * @author Brett Meyer
 */
@RunWith(Parameterized.class)
public abstract class AbstractTest {
    
    protected final Binding binding;
    
    @Parameters
    public static List<Binding[]> bindings() {
        return BindingFactory.getBindings();
    }
    
    public AbstractTest(Binding binding) {
        this.binding = binding;
    }
    
    @After
    public void cleanup() throws Exception {
        binding.deleteAll();
    }
    
    protected void verifyArtifacts(List<BaseArtifactType> artifacts) {
        for (BaseArtifactType artifact : artifacts) {
            verifyArtifact(artifact);
        }
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
    
    protected final static WsdlDocument WsdlDocument() throws Exception {
        return (WsdlDocument) ArtifactType.WsdlDocument().newArtifactInstance();
    }
    protected final static XsdDocument XsdDocument() throws Exception {
        return (XsdDocument) ArtifactType.XsdDocument().newArtifactInstance();
    }
    protected final static ExtendedDocument ExtendedDocument(String extendedType) throws Exception {
        return (ExtendedDocument) ArtifactType.ExtendedDocument(extendedType).newArtifactInstance();
    }

}
