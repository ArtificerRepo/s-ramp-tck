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

import org.jboss.resteasy.plugins.providers.atom.Entry;
import org.jboss.resteasy.plugins.providers.atom.Link;
import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Relationship;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Target;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XmlDocument;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.MediaType;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Brett Meyer
 */
public class Test_2_3 extends AbstractAtomTest {

    @Test
    public void test_2_3_1() throws Exception {
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
        // TODO: The spec isn't clear on this.  The artifact content type should simply be application/xml,
        // but the links are application/atom+xml;type='entry'.  *content* != *atom* link
//        assertEquals(artifact.getContentType(), entry.getLinks().get(0).getType().toString());
        assertEquals(artifact.getContentType() + ";charset=" + artifact.getContentEncoding(),
                entry.getContent().getType().toString());
        assertEquals(AtomBinding.BASE_URL + "/s-ramp/core/XmlDocument/" + artifact.getUuid(),
                entry.getContent().getSrc().toString());
    }

    @Test
    public void test_2_3_2() throws Exception {
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
    
    @Test
    public void test_2_3_5_wrongCollection() throws Exception {
        // Purposefully send an artifact to the wrong collection endpoint.
        String atomUrl = AtomBinding.BASE_URL + "/s-ramp/xsd/XsdDocument";
        ExtendedArtifactType artifact = ExtendedArtifactType("FooType");
        artifact.setName("FooName");
        Builder clientRequest = binding.getClientRequest(atomUrl);
        Response response = clientRequest.post(Entity.entity(SrampAtomUtils.wrapSrampArtifact(artifact),
                MediaType.APPLICATION_ATOM_XML_ENTRY));
        assertEquals(403, response.getStatus());
    }
    
    @Test
    public void test_2_3_5_1() throws Exception {
        ExtendedArtifactType artifact = ExtendedArtifactType("FooType");
        artifact.setName("FooName");
        artifact.setUuid(UUID.randomUUID().toString());
        
        // A non-existent artifact cannot be updated.
        binding.update(artifact, 404);
        
        // An existing UUID cannot be duplicated.
        binding.create(artifact);
        binding.create(artifact, 409);
    }
    
    @Test
    public void test_2_3_5_3() throws Exception {
        // A non-existent artifact cannot be retrieved.
        binding.get(UUID.randomUUID().toString(), ArtifactType.ExtendedArtifactType("FooType", false), 404);
    }
}
