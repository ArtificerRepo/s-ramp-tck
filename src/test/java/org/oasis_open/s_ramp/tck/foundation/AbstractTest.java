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

import java.util.List;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Document;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.PolicyDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.WsdlDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XmlDocument;
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

    protected final static Document Document() throws Exception {
        return (Document) ArtifactType.Document().newArtifactInstance();
    }
    protected final static XmlDocument XmlDocument() throws Exception {
        return (XmlDocument) ArtifactType.XmlDocument().newArtifactInstance();
    }
    protected final static XsdDocument XsdDocument() throws Exception {
        return (XsdDocument) ArtifactType.XsdDocument().newArtifactInstance();
    }
    protected final static WsdlDocument WsdlDocument() throws Exception {
        return (WsdlDocument) ArtifactType.WsdlDocument().newArtifactInstance();
    }
    protected final static PolicyDocument PolicyDocument() throws Exception {
        return (PolicyDocument) ArtifactType.PolicyDocument().newArtifactInstance();
    }
    protected final static ExtendedArtifactType ExtendedArtifactType(String extendedType) throws Exception {
        return (ExtendedArtifactType) ArtifactType.ExtendedArtifactType(extendedType, false).newArtifactInstance();
    }
    protected final static ExtendedDocument ExtendedDocument(String extendedType) throws Exception {
        return (ExtendedDocument) ArtifactType.ExtendedDocument(extendedType).newArtifactInstance();
    }

}
