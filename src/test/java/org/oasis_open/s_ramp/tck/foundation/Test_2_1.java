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

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Relationship;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XsdDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XsdDocumentEnum;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XsdDocumentTarget;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class Test_2_1 extends CoreModelTest {

    public Test_2_1(Binding binding) {
        super(binding);
    }
    
    @Test
    public void test_2_1_2() throws Exception {
        XsdDocument xsd1 = XsdDocument();
        BaseArtifactType xsdArtifact1 = binding.upload(xsd1, "/PO.xsd");
        verifyArtifact(xsdArtifact1);
        
        XsdDocument xsd2 = XsdDocument();
        
        XsdDocumentTarget xsdTarget = new XsdDocumentTarget();
        xsdTarget.setValue(xsdArtifact1.getUuid());
        xsdTarget.setArtifactType(XsdDocumentEnum.XSD_DOCUMENT);

        // create a valid generic relationship w/ a target
        Relationship validRelationship = new Relationship();
        validRelationship.setRelationshipType("similarXsds");
        validRelationship.getRelationshipTarget().add(xsdTarget);
        xsd2.getRelationship().add(validRelationship);

        // create a valid generic relationship w/ no target
        // TODO FAILURE: SRAMP-552
//        Relationship validRelationshipNoTarget = new Relationship();
//        validRelationship.setRelationshipType("foo");
//        xsd2.getRelationship().add(validRelationshipNoTarget);
        
        // TODO: After SRAMP-552, test that only 1 instance of a relationship type, with *no* target, can be added to a source
        // TODO: After SRAMP-552, test that instances of a relationship type cannot have both target(s) and no target
        
        XsdDocument xsd2Artifact = (XsdDocument) binding.upload(xsd2, "/PO.xsd");
        verifyArtifact(xsd2Artifact);
        
        // TODO FAILURE: SRAMP-552
//        assertEquals(2, xsd2Artifact.getRelationship().size());
        
        // generic relationships cannot have the same type as a derived/modeled relationship
        xsd2.getRelationship().clear();
        Relationship invalidRelationship = new Relationship();
        invalidRelationship.setRelationshipType("importedXsds");
        invalidRelationship.getRelationshipTarget().add(xsdTarget);
        xsd2.getRelationship().add(invalidRelationship);
        
        // TODO FAILURE: SRAMP-551 (either 0 relationships should result, or the upload should fail)
//        xsd2Artifact = (XsdDocument) binding.upload(xsd2, "/PO.xsd");
//        verifyArtifact(xsd2Artifact);
        
        // test that a derived relationship cannot be created/edited
        xsd2.getImportedXsds().add(xsdTarget);
        xsd2Artifact = (XsdDocument) binding.upload(xsd2, "/PO.xsd");
        verifyArtifact(xsd2Artifact);
        // TODO: Should the upload fail instead?  What does the atom binding spec say?
        assertEquals(0, xsd2Artifact.getImportedXsds().size());
        
        // TODO: Test modeled relationships between soa/serviceimpl after SRAMP-167
    }

}
