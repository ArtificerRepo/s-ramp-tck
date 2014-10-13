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
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.atom.Category;
import org.jboss.resteasy.plugins.providers.atom.Entry;
import org.jboss.resteasy.plugins.providers.atom.Feed;
import org.jboss.resteasy.plugins.providers.atom.Link;
import org.jboss.resteasy.plugins.providers.multipart.MultipartConstants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartRelatedOutput;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.StoredQuery;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;
import org.oasis_open.s_ramp.tck.MediaType;
import org.oasis_open.s_ramp.tck.SrampAtomConstants;

/**
 * @author Brett Meyer
 */
public class AtomBinding extends Binding {
    
//    public static final String BASE_URL = System.getProperty("password");
    public static final String BASE_URL = "http://localhost:8080/s-ramp-server";

    private static final QName DERIVED_QNAME = new QName(
            "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "derived", "s-ramp");
    
    private static final String NAMESPACE = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0";
    
    @Override
    public BaseArtifactType get(String uuid, ArtifactType type) throws Exception {
        String atomUrl = getUrl(type) + "/" + uuid;
        Entry entry = getArtifact(atomUrl);
        verifyEntry(entry);
        return SrampAtomUtils.unwrapSrampArtifact(entry);
    }

    @Override
    public List<BaseArtifactType> query(String query) throws Exception {
        List<BaseArtifactType> artifacts = new ArrayList<BaseArtifactType>();
        Feed feed = getFeed(query);
        for (Entry entry : feed.getEntries()) {
            for (Link link : entry.getLinks()) {
                // TODO: Safe assumption for all impls?
                if ("self".equals(link.getRel())) {
                    artifacts.add(SrampAtomUtils.unwrapSrampArtifact(getArtifact(link.getHref().toString())));
                }
            }
        }
        return artifacts;
    }
    
    @Override
    public BaseArtifactType create(BaseArtifactType artifact) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        String atomUrl = getUrl(artifactType);
        Entry entry = create(atomUrl, SrampAtomUtils.wrapSrampArtifact(artifact));
        return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
    }
    
    @Override
    public StoredQuery create(StoredQuery storedQuery) throws Exception {
        String atomUrl = BASE_URL + "/s-ramp/query";
        Entry entry = create(atomUrl, SrampAtomUtils.wrapStoredQuery(storedQuery));
        return SrampAtomUtils.unwrapStoredQuery(entry);
    }
    
    private Entry create(String atomUrl, Entry entry) {
        Builder clientRequest = getClientRequest(atomUrl);
        Response response = clientRequest.post(Entity.entity(entry,
                MediaType.APPLICATION_ATOM_XML_ENTRY));
        checkResponse(response);
        entry = response.readEntity(Entry.class);
        verifyEntry(entry);
        return entry;
    }

    @Override
    public BaseArtifactType upload(ArtifactType artifactType, String filePath) throws Exception {
        String atomUrl = getUrl(artifactType);
        Builder clientRequest = getClientRequest(atomUrl);
        
        InputStream is = this.getClass().getResourceAsStream(filePath);
        String text = convertStreamToString(is);
        is.close();

        String fileName = filePath.substring( filePath.lastIndexOf('/') + 1, filePath.length() );
        clientRequest.header("Slug", fileName);

        Response response = clientRequest.post(Entity.entity(text, artifactType.getMimeType()));
        checkResponse(response);
        Entry entry = response.readEntity(Entry.class);
        verifyEntry(entry);
        return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
    }
    
    @Override
    public BaseArtifactType upload(BaseArtifactType artifact, String filePath) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        Entry entry = uploadReturnEntry(artifact, filePath);
        return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
    }
    
    public Entry uploadReturnEntry(BaseArtifactType artifact, String filePath) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        String atomUrl = getUrl(artifactType);
        Builder clientRequest = getClientRequest(atomUrl);

        MultipartRelatedOutput output = new MultipartRelatedOutput();

        //1. Add first part, the S-RAMP entry
        Entry atomEntry = SrampAtomUtils.wrapSrampArtifact(artifact);

        MediaType mediaType = new MediaType("application", "atom+xml");
        output.addPart(atomEntry, mediaType);

        //2. Add second part, the content
        InputStream is = this.getClass().getResourceAsStream(filePath);
        String text = convertStreamToString(is);
        is.close();
        output.addPart(text, MediaType.valueOf(artifactType.getMimeType()));

        //3. Send the request
        Response response = clientRequest.post(Entity.entity(output, MultipartConstants.MULTIPART_RELATED));
        checkResponse(response);
        Entry entry = response.readEntity(Entry.class);
        verifyEntry(entry);
        return entry;
    }
    
    public void update(BaseArtifactType artifact) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        String atomUrl = getUrl(artifactType) + "/" + artifact.getUuid();
        
        Builder clientRequest = getClientRequest(atomUrl);
        Response response = clientRequest.put(Entity.entity(SrampAtomUtils.wrapSrampArtifact(artifact),
                MediaType.APPLICATION_ATOM_XML_ENTRY));
        checkResponse(response);
    }
    
    public void uploadOntology(String filePath) throws Exception {
        // TODO: This may not be required by the spec.
        InputStream is = this.getClass().getResourceAsStream(filePath);
        Builder clientRequest = getClientRequest(BASE_URL + "/s-ramp/ontology");
        Response response = clientRequest.post(Entity.entity(is, MediaType.APPLICATION_RDF_XML));
        checkResponse(response);
    }
    
    @Override
    public void deleteAll() throws Exception {
        Feed feed = getFeed("/s-ramp?query=/s-ramp&startIndex=0&count=10000");
        for (Entry entry : feed.getEntries()) {
            // Delete all primary artifacts
            if (entry.getExtensionAttributes().containsKey(DERIVED_QNAME)
                    && "false".equals(entry.getExtensionAttributes().get(DERIVED_QNAME))) {
                for (Link link : entry.getLinks()) {
                    // TODO: Safe assumption for all impls?
                    if ("self".equals(link.getRel())) {
                        getClientRequest(link.getHref().toString()).delete();
                    }
                }
            }
        }
        
        feed = getFeed("/s-ramp/query");
        for (Entry entry : feed.getEntries()) {
            String queryName = entry.getId().toString().replace("urn:uuid:", "");
            getClientRequest(BASE_URL + "/s-ramp/query/" + queryName).delete();
        }
        
        // TODO: This may not be required by the spec.
        feed = getFeed("/s-ramp/ontology");
        for (Entry entry : feed.getEntries()) {
            String uuid = entry.getId().toString().replace("urn:uuid:", "");
            getClientRequest(BASE_URL + "/s-ramp/ontology/" + uuid).delete();
        }
    }
    
    @Override
    public String getUrl(BaseArtifactType artifact) {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        return getUrl(artifactType);
    }
    
    @Override
    public String getUrl(ArtifactType artifactType) {
        String type = artifactType.getType();
        return String.format("%1$s/%2$s/%3$s", BASE_URL + "/s-ramp",
                artifactType.getArtifactType().getModel(), type);
    }
    
    private Feed getFeed(String endpoint) {
        Builder clientRequest = getClientRequest(BASE_URL + endpoint);
        Feed feed = clientRequest.get(Feed.class);
        verifyFeed(feed);
        return feed;
    }
    
    private Entry getArtifact(String url) {
        Builder clientRequest = getClientRequest(url);
        Entry entry = clientRequest.get(Entry.class);
        verifyEntry(entry);
        return entry;
    }
    
    public Builder getClientRequest(String endpoint) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(new BasicAuthFilter());
        return client.target(endpoint).request();
    }
    
    private String convertStreamToString(java.io.InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            return "";
        }
    }
    
    private void checkResponse(Response response) {
        if (response.getStatus() < 200 || response.getStatus() > 206) {
            fail("Server responded with status " + response.getStatus() + ".  Check the logs for issues.");
        }
    }
    
    private void verifyFeed(Feed feed) {
        for (Object key : feed.getExtensionAttributes().keySet()) {
            QName qname = (QName) key;
            // Foundation 1.9
            assertEquals(NAMESPACE, qname.getNamespaceURI());
        }
        for (Entry entry : feed.getEntries()) {
            verifyEntry(entry);
      }
    }
    
    private void verifyEntry(Entry entry) {
        for (Object key : entry.getExtensionAttributes().keySet()) {
            QName qname = (QName) key;
            // Foundation 1.9
            assertEquals(NAMESPACE, qname.getNamespaceURI());
        }
        
        // Atom 2.2
        assertTrue(entry.getId().toString().contains("urn:uuid:"));
        
        // Atom 2.3.1
        assertTrue(entry.getCategories().size() > 0);
        Category category = findCategory(SrampAtomConstants.X_S_RAMP_TYPE, entry);
        assertNotNull(category);
        if (category.getTerm().equals("query") || category.getTerm().equals("classification")) {
            // Do nothing: stored query or classification.
        } else {
            // Else, assume it's an artifact.
            ArtifactType artifactType = SrampAtomUtils.getArtifactTypeFromEntry(entry);
            // TODO: Not sure if this is correct.  In Overlord, the term is the name of the extended type,
            // not "ExtendedArtifactType"
            if (! artifactType.isExtendedType()) {
                assertEquals(artifactType.getArtifactType().getType(), category.getTerm());
            }
        }
        // TODO: Test the "kind" category after SRAMP-596.  urn:x-sramp:2013:kind must be "derived", "modeled", or "generic"
    }
    
    private Category findCategory(String scheme, Entry entry) {
        for (Category category : entry.getCategories()) {
            if (category.getScheme().toString().equals(scheme)) {
                return category;
            }
        }
        return null;
    }
    
    public static class BasicAuthFilter implements ClientRequestFilter {

//        private static final String USERNAME = System.getProperty("username");
//        private static final String PASSWORD = System.getProperty("password");
        private static final String USERNAME = "admin";
        private static final String PASSWORD = "overlord1!";

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            String token = USERNAME + ":" + PASSWORD;
            String base64Token = Base64.encodeBase64String(token.getBytes(StandardCharsets.UTF_8));
            requestContext.getHeaders().add("Authorization", "Basic " + base64Token);
        }
    }

}
