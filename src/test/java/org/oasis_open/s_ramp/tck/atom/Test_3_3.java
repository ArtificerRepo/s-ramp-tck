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
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.StoredQuery;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Brett Meyer.
 */
public class Test_3_3 extends AbstractAtomTest {

    @Test
    public void testStoredQueryName() throws Exception {
        StoredQuery storedQuery = new StoredQuery();
        // test invalid name
        storedQuery.setQueryName("");
        storedQuery.setQueryExpression("/s-ramp/xsd/XsdDocument");
        binding.create(storedQuery, 409);

        storedQuery.setQueryName("storedQuery");
        binding.create(storedQuery, 200);
        // test non-unique name
        binding.create(storedQuery, 409);

        // test invalid name
        binding.storedQuery("doesNotExist", "", 404);
    }

    @Test
    public void testStoredQueryPaging() throws Exception {
        for (int i = 0; i < 9; i++) {
            ExtendedArtifactType artifact = ExtendedArtifactType("FooType");
            artifact.setName("Foo" + i);
            Property property = new Property();
            property.setPropertyName("FooKey");
            property.setPropertyValue("FooValue");
            artifact.getProperty().add(property);
            binding.create(artifact);
        }

        StoredQuery storedQuery = new StoredQuery();
        storedQuery.setQueryName("storedQuery");
        storedQuery.setQueryExpression("/s-ramp/ext/FooType");
        // Also testing 'propertyName'
        storedQuery.getPropertyName().add("name");
        storedQuery.getPropertyName().add("FooKey");
        binding.create(storedQuery);

        assertResults(0, 4);
        assertResults(4, 4);
        assertResults(8, 1);
    }

    private void assertResults(int startIndex, int expectedCount) throws Exception {
        List<BaseArtifactType> artifacts = binding.storedQuery("storedQuery",
                "startIndex=" + startIndex + "&count=4&orderBy=name&ascending=true",
                200);
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
}
