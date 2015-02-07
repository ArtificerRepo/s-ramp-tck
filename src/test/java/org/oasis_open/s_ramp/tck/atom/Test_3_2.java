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

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Property;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Brett Meyer.
 */
public class Test_3_2 extends AbstractAtomTest {

    @Test
    public void testPaging() throws Exception {
        for (int i = 0; i < 9; i++) {
            ExtendedArtifactType artifact = ExtendedArtifactType("FooType");
            artifact.setName("Foo" + i);
            Property property = new Property();
            property.setPropertyName("FooKey");
            property.setPropertyValue("FooValue");
            artifact.getProperty().add(property);
            binding.create(artifact);
        }

        assertResults(0, 4);
        assertResults(4, 4);
        assertResults(8, 1);
    }

    private void assertResults(int startIndex, int expectedCount) throws Exception {
        // Also testing 'propertyName'
        List<BaseArtifactType> artifacts = binding.query("/s-ramp/ext/FooType&"
                + "startIndex=" + startIndex + "&count=4&orderBy=name&ascending=true&propertyName=name&propertyName=FooKey");
        assertEquals(expectedCount, artifacts.size());
        for (int i = 0; i < expectedCount; i++) {
            int index = i + startIndex;
            BaseArtifactType artifact = artifacts.get(i);
            assertEquals("FooKey", artifact.getProperty().get(0).getPropertyName());
            assertEquals("FooValue", artifact.getProperty().get(0).getPropertyValue());
            // assert ordering worked
            assertEquals("Foo" + index, artifact.getName());
        }
    }

    @Test
    public void testEmptyResults() throws Exception {
        List<BaseArtifactType> artifacts = binding.query("/s-ramp/ext/NotReal");
        assertEquals(0, artifacts.size());
    }
}
