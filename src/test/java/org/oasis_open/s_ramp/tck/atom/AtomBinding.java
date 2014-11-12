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
 * Provides an Atom binding for the Foundational spec tests.  In addition, this provides coverage of much of the
 * Atom binding spec, where possible.  Additional coverage is provided by the tests in the *.atom package.
 * 
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
        return get(uuid, type, 200);
    }
    
    public BaseArtifactType get(String uuid, ArtifactType type, int expectedResponse) throws Exception {
        String atomUrl = getUrl(type) + "/" + uuid;
        Entry entry = getArtifact(atomUrl, expectedResponse);
        if (entry != null) {
            return SrampAtomUtils.unwrapSrampArtifact(entry);
        } else {
            return null;
        }
    }
    
    private void verifyMedia(String uuid, ArtifactType type) {
        String atomUrl = getUrl(type) + "/" + uuid + "/media";
        Builder clientRequest = getClientRequest(atomUrl);
        Response response = clientRequest.get();
        verifyResponse(response, 200);
        ArrayList<?> responseContentTypeValues = (ArrayList<?>) response.getMetadata().get("Content-Type");
        assertEquals(type.getMimeType(), responseContentTypeValues.get(0));
    }

    @Override
    public List<BaseArtifactType> query(String query) throws Exception {
        String path = "/s-ramp?query=" + query;
        List<BaseArtifactType> artifacts = new ArrayList<BaseArtifactType>();
        Feed feed = getFeed(path);
        for (Entry entry : feed.getEntries()) {
            for (Link link : entry.getLinks()) {
                // TODO: Safe assumption for all impls?
                if ("self".equals(link.getRel())) {
                    artifacts.add(SrampAtomUtils.unwrapSrampArtifact(getArtifact(link.getHref().toString(), 200)));
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
    
    public Entry create(String atomUrl, Entry entry) {
        Response response = create(atomUrl, entry, 200);
        entry = response.readEntity(Entry.class);
        verifyEntry(entry);
        return entry;
    }
    
    public Response create(String atomUrl, Entry entry, int expectedResponse) {
        Builder clientRequest = getClientRequest(atomUrl);
        Response response = clientRequest.post(Entity.entity(entry,
                MediaType.APPLICATION_ATOM_XML_ENTRY));
        verifyResponse(response, expectedResponse);
        return response;
    }
    
    public Response create(BaseArtifactType artifact, int expectedResponse) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        String atomUrl = getUrl(artifactType);
        return create(atomUrl, SrampAtomUtils.wrapSrampArtifact(artifact), expectedResponse);
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
        verifyResponse(response, 200);
        Entry entry = response.readEntity(Entry.class);
        verifyEntry(entry);
        return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
    }
    
    @Override
    public BaseArtifactType upload(BaseArtifactType artifact, String filePath) throws Exception {
        return upload(artifact, filePath, 200);
    }
    
    @Override
    public BaseArtifactType upload(BaseArtifactType artifact, String filePath, int expectedResponse) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        Entry entry = uploadReturnEntry(artifact, filePath, expectedResponse);
        if (entry != null) {
            return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
        } else {
            return null;
        }
    }
    
    public Entry uploadReturnEntry(BaseArtifactType artifact, String filePath) throws Exception {
        return uploadReturnEntry(artifact, filePath, 200);
    }
    
    public Entry uploadReturnEntry(BaseArtifactType artifact, String filePath, int expectedResponse) throws Exception {
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
        boolean continueProcessing = verifyResponse(response, expectedResponse);
        if (continueProcessing) {
            Entry entry = response.readEntity(Entry.class);
            verifyEntry(entry);
            return entry;
        } else {
            return null;
        }
    }
    
    @Override
    public void update(BaseArtifactType artifact) throws Exception {
        update(artifact, 204);
    }
    
    public void update(BaseArtifactType artifact, int expectedResponse) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        String atomUrl = getUrl(artifactType) + "/" + artifact.getUuid();
        
        Builder clientRequest = getClientRequest(atomUrl);
        Response response = clientRequest.put(Entity.entity(SrampAtomUtils.wrapSrampArtifact(artifact),
                MediaType.APPLICATION_ATOM_XML_ENTRY));
        verifyResponse(response, expectedResponse);
    }
    
    @Override
    public void uploadOntology(String filePath) throws Exception {
        // TODO: This may not be required by the spec.
        InputStream is = this.getClass().getResourceAsStream(filePath);
        Builder clientRequest = getClientRequest(BASE_URL + "/s-ramp/ontology");
        Response response = clientRequest.post(Entity.entity(is, MediaType.APPLICATION_RDF_XML));
        verifyResponse(response, 200);
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
    
    private Entry getArtifact(String url, int expectedResponse) {
        Builder clientRequest = getClientRequest(url);
        Response response = clientRequest.get();
        boolean continueProcessing = verifyResponse(response, expectedResponse);
        if (continueProcessing) {
            Entry entry = response.readEntity(Entry.class);
            verifyEntry(entry);
            return entry;
        } else {
            return null;
        }
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
    
    private boolean verifyResponse(Response response, int expectedResponse) {
        assertEquals("Server responded with an unexpected HTTP status.", expectedResponse, response.getStatus());
        // True if we should continue processing, such as expecting an Entry to be returned.
        return response.getStatus() >= 200 && response.getStatus() <= 206;
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
        String uuid = entry.getId().toString().replace("urn:uuid:", "");
        
        // Atom 2.3.1
        assertTrue(entry.getCategories().size() > 0);
        Category typeCategory = findCategory(SrampAtomConstants.X_S_RAMP_TYPE, entry);
        assertNotNull(typeCategory);
        if (typeCategory.getTerm().equals("query") || typeCategory.getTerm().equals("classification")) {
            // Do nothing: stored query or classification.
        } else {
            // Else, assume it's an artifact.
            ArtifactType artifactType = SrampAtomUtils.getArtifactTypeFromEntry(entry);
            if (artifactType.isDocument()) {
                // Atom 2.3.5.3 -- verify /media returns the content.
                verifyMedia(uuid, artifactType);
            }
            // TODO: Not sure if this is correct.  In Overlord, the term is the name of the extended type,
            // not "ExtendedArtifactType"
            if (! artifactType.isExtendedType()) {
                assertEquals(artifactType.getArtifactType().getType(), typeCategory.getTerm());
            }
            
            // verify :kind
            Category kindCategory = findCategory(SrampAtomConstants.X_S_RAMP_KIND, entry);
            assertNotNull(kindCategory);
            if (artifactType.isDerived()) {
                assertEquals("derived", kindCategory.getTerm());
            } else {
                assertEquals("modeled", kindCategory.getTerm());
            }
        }
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
