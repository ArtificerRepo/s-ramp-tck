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
package org.oasis_open.s_ramp.tck;

import java.util.List;

import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.StoredQuery;

/**
 * @author Brett Meyer
 */
// TODO: Could be an interface if it ends up that there's nothing abstract to provide.
public abstract class Binding {
    
    public abstract BaseArtifactType get(String uuid, ArtifactType type) throws Exception;
    
    public abstract List<BaseArtifactType> query(String query) throws Exception;
    
    public abstract BaseArtifactType create(BaseArtifactType artifact) throws Exception;
    
    public abstract StoredQuery create(StoredQuery storedQuery) throws Exception;
    
    public abstract BaseArtifactType upload(ArtifactType artifactType, String filePath) throws Exception;
    
    public abstract BaseArtifactType upload(BaseArtifactType artifact, String filePath) throws Exception;
    
    public abstract void update(BaseArtifactType artifact) throws Exception;
    
    public abstract void uploadOntology(String filePath) throws Exception;
    
    public abstract void deleteAll() throws Exception;
    
    public abstract String getUrl(BaseArtifactType artifact);
    
    public abstract String getUrl(ArtifactType artifactType);
}
