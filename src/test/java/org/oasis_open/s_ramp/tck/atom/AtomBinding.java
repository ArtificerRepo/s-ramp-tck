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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.atom.Entry;
import org.jboss.resteasy.plugins.providers.atom.Feed;
import org.jboss.resteasy.plugins.providers.atom.Link;
import org.jboss.resteasy.plugins.providers.multipart.MultipartConstants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartRelatedOutput;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class AtomBinding extends Binding {
    
//    private static final String BASE_URL = System.getProperty("password");
    private static final String BASE_URL = "http://localhost:8080/s-ramp-server";

    @Override
    public List<BaseArtifactType> query(String query) throws Exception {
        List<BaseArtifactType> artifacts = new ArrayList<BaseArtifactType>();
        Feed feed = getFeed(query);
        for (Entry entry : feed.getEntries()) {
//            artifacts.add(SrampAtomUtils.unwrapSrampArtifact(entry));
            // TODO: The feed doesn't include sramp:artifact on the entities, so unwrapping doesn't work.  Should
            // the below be necessary?
            
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
    public BaseArtifactType upload(ArtifactType artifactType, String filePath) throws Exception {
        String type = artifactType.getType();
        String atomUrl = String.format("%1$s/%2$s/%3$s", "/s-ramp", //$NON-NLS-1$
                artifactType.getArtifactType().getModel(), type);
        Builder clientRequest = getClientRequest(BASE_URL + atomUrl);
        
        InputStream is = this.getClass().getResourceAsStream(filePath);
        String text = convertStreamToString(is);
        is.close();

        String fileName = filePath.substring( filePath.lastIndexOf('/') + 1, filePath.length() );
        clientRequest.header("Slug", fileName); //$NON-NLS-1$

        Response response = clientRequest.post(Entity.entity(text, artifactType.getMimeType()));

        Entry entry = response.readEntity(Entry.class);
        return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
    }
    
    @Override
    public BaseArtifactType upload(BaseArtifactType artifact, String filePath) throws Exception {
        ArtifactType artifactType = ArtifactType.valueOf(artifact);
        String type = artifactType.getType();
        String atomUrl = String.format("%1$s/%2$s/%3$s", BASE_URL + "/s-ramp", //$NON-NLS-1$
                artifactType.getArtifactType().getModel(), type);
        Builder clientRequest = getClientRequest(atomUrl);

        MultipartRelatedOutput output = new MultipartRelatedOutput();

        //1. Add first part, the S-RAMP entry
        Entry atomEntry = SrampAtomUtils.wrapSrampArtifact(artifact);

        MediaType mediaType = new MediaType("application", "atom+xml"); //$NON-NLS-1$ //$NON-NLS-2$
        output.addPart(atomEntry, mediaType);

        //2. Add second part, the content
        InputStream is = this.getClass().getResourceAsStream(filePath);
        String text = convertStreamToString(is);
        is.close();
        MediaType mediaType2 = MediaType.valueOf(artifactType.getMimeType());
        output.addPart(text, mediaType2);

        //3. Send the request
        Response response = clientRequest.post(Entity.entity(output, MultipartConstants.MULTIPART_RELATED));
        Entry entry = response.readEntity(Entry.class);
        return SrampAtomUtils.unwrapSrampArtifact(artifactType, entry);
    }
    
    @Override
    public void delete(BaseArtifactType artifact) throws Exception {
        ArtifactType type = ArtifactType.valueOf(artifact);
        String artifactModel = type.getArtifactType().getModel();
        String artifactType = type.getArtifactType().getType();
        if ("ext".equals(type.getArtifactType().getModel()) && type.getExtendedType()!=null) { //$NON-NLS-1$
            artifactType = type.getExtendedType();
        }
        String atomUrl = String.format("%1$s/%2$s/%3$s/%4$s", BASE_URL + "/s-ramp", artifactModel, artifactType,
                artifact.getUuid()); //$NON-NLS-1$
        Builder clientRequest = getClientRequest(atomUrl);
        clientRequest.delete();
    }
    
    private Feed getFeed(String endpoint) {
        Builder clientRequest = getClientRequest(BASE_URL + endpoint);
        Feed feed = clientRequest.get(Feed.class);
        
        // TODO: Verify using the spec
        
        return feed;
//        String value = response.readEntity(String.class);
//        feed.close();
//        return value;
    }
    
    private Entry getArtifact(String url) {
        Builder clientRequest = getClientRequest(url);
        Entry entry = clientRequest.get(Entry.class);
        
        // TODO: Verify using the spec
        
        return entry;
    }
    
    private Builder getClientRequest(String endpoint) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        client.register(new BasicAuthFilter());
        return client.target(endpoint).request();
    }
    
    private String convertStreamToString(java.io.InputStream is) {
        try {
            return new Scanner(is).useDelimiter("\\A").next(); //$NON-NLS-1$
        } catch (java.util.NoSuchElementException e) {
            return ""; //$NON-NLS-1$
        }
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
