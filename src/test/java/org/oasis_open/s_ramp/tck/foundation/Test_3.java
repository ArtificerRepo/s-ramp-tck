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

import java.util.List;

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class Test_3 extends CoreModelTest {

    public Test_3(Binding binding) {
        super(binding);
    }

    @Test
    public void testClassifiers() throws Exception {
        // TODO: This may not be required by the spec.
        binding.uploadOntology("/regional.owl.xml");
        
        ExtendedArtifactType artifact = ExtendedArtifactType("FooType");
        artifact.getClassifiedBy().add("Europe");
        BaseArtifactType artifact1 = binding.create(artifact);
        verifyArtifact(artifact1);
        
        artifact = ExtendedArtifactType("FooType");
        artifact.getClassifiedBy().add("Europe");
        artifact.getClassifiedBy().add("Asia");
        BaseArtifactType artifact2 = binding.create(artifact);
        verifyArtifact(artifact2);
        
        artifact = ExtendedArtifactType("FooType");
        artifact.getClassifiedBy().add("World");
        BaseArtifactType artifact3 = binding.create(artifact);
        verifyArtifact(artifact3);
        
        // TODO: Technically optional in the spec conformance section, but *shouldn't* be.  Will hopefully be required
        // in future versions.
        List<BaseArtifactType> artifacts = binding.query("/s-ramp/ext/FooType[classifiedByAnyOf(., 'Europe')]");
        verifyArtifacts(artifacts);
        assertEquals(2, artifacts.size());
        artifacts = binding.query("/s-ramp/ext/FooType[classifiedByAnyOf(., 'Asia')]");
        verifyArtifacts(artifacts);
        assertEquals(1, artifacts.size());
        artifacts = binding.query("/s-ramp/ext/FooType[classifiedByAnyOf(., 'World')]");
        verifyArtifacts(artifacts);
        assertEquals(3, artifacts.size());

        artifact1.getClassifiedBy().clear();
        artifact2.getClassifiedBy().clear();
        artifact3.getClassifiedBy().clear();
        binding.update(artifact1);
        binding.update(artifact2);
        binding.update(artifact3);
        
        artifacts = binding.query("/s-ramp/ext/FooType[classifiedByAnyOf(., 'Europe')]");
        assertEquals(0, artifacts.size());
        artifacts = binding.query("/s-ramp/ext/FooType[classifiedByAnyOf(., 'Asia')]");
        assertEquals(0, artifacts.size());
        artifacts = binding.query("/s-ramp/ext/FooType[classifiedByAnyOf(., 'World')]");
        assertEquals(0, artifacts.size());
    }
}
