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

import java.util.UUID;

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;

/**
 * Note: Only tests requirements not already covered in AtomBinding's assertions.
 * 
 * @author Brett Meyer
 */
public class Test_2_2 extends AbstractAtomTest {
    
    @Test
    public void test_2_2() throws Exception {
        BaseArtifactType artifact1 = XsdDocument();
        artifact1.setUuid(UUID.randomUUID().toString());
        BaseArtifactType uploadedXsd = binding.upload(artifact1, "/PO.xsd");
        assertEquals(artifact1.getUuid(), uploadedXsd.getUuid());
    }
}
