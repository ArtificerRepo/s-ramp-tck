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
package org.oasis_open.s_ramp.tck.atom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.resteasy.plugins.providers.atom.Category;
import org.jboss.resteasy.plugins.providers.atom.Entry;
import org.jboss.resteasy.plugins.providers.atom.Link;
import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Relationship;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Target;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XmlDocument;

/**
 * @author Brett Meyer
 */
public class Test_2_3 extends AbstractAtomTest {

    @Test
    public void testAtomElementMappings() throws Exception {
        XmlDocument artifact = XmlDocument();
        artifact.setCreatedBy("admin");
        artifact.setLastModifiedBy("admin");
        artifact.setName("PO");
        artifact.setDescription("Foo PO");
        Entry entry = binding.uploadReturnEntry(artifact, "/PO.xml");
        artifact = (XmlDocument) SrampAtomUtils.unwrapSrampArtifact(entry);
        
        assertEquals(artifact.getCreatedBy(), entry.getAuthors().get(0).getName());
        assertEquals(artifact.getUuid(), entry.getId().toString().replace("urn:uuid:", ""));
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(entry.getPublished());
        XMLGregorianCalendar gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        assertEquals(artifact.getCreatedTimestamp(), gc);
        c.setTime(entry.getUpdated());
        gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        assertEquals(artifact.getLastModifiedTimestamp(), gc);
        assertEquals(artifact.getName(), entry.getTitle());
        assertEquals(artifact.getDescription(), entry.getSummary());
        assertEquals(artifact.getContentType(), entry.getLinks().get(0).getType().toString());
        // TODO: FAILURE SRAMP-595
//        assertEquals(artifact.getContentEncoding(), entry.getContent().getType().toString());
    }

    @Test
    public void testLinkValues() throws Exception {
        XmlDocument artifact1 = XmlDocument();
        artifact1.setName("PO 1");
        Entry entry1 = binding.uploadReturnEntry(artifact1, "/PO.xml");
        artifact1 = (XmlDocument) SrampAtomUtils.unwrapSrampArtifact(entry1);
        
        XmlDocument artifact2 = XmlDocument();
        artifact2.setName("PO 2");
        Target target = new Target();
        target.setValue(artifact1.getUuid());
        Relationship relationship = new Relationship();
        relationship.setRelationshipType("fooRelationship");
        relationship.getRelationshipTarget().add(target);
        artifact2.getRelationship().add(relationship);
        Entry entry2 = binding.uploadReturnEntry(artifact2, "/PO.xml");
        artifact2 = (XmlDocument) SrampAtomUtils.unwrapSrampArtifact(entry2);
        
        // TODO: Improve the assertions.  Simply looking for the UUID is probably overly-simple.
        Link link = entry2.getLinkByRel("self");
        assertNotNull(link);
        assertTrue(link.getHref().toString().contains(artifact2.getUuid()));
        link = entry2.getLinkByRel("edit-media");
        assertNotNull(link);
        assertTrue(link.getHref().toString().contains(artifact2.getUuid()));
        link = entry2.getLinkByRel("edit");
        assertNotNull(link);
        assertTrue(link.getHref().toString().contains(artifact2.getUuid()));
    }
}
