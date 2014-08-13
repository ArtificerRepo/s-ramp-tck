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

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.s_ramp.tck.Binding;
import org.oasis_open.s_ramp.tck.BindingFactory;

/**
 * @author Brett Meyer
 */
@RunWith(Parameterized.class)
public abstract class AbstractTest {
    
    protected final Binding binding;
    
    protected final List<BaseArtifactType> createdArtifacts = new ArrayList<BaseArtifactType>();
    
    @Parameters
    public static List<Binding[]> bindings() {
        return BindingFactory.getBindings();
    }
    
    public AbstractTest(Binding binding) {
        this.binding = binding;
    }
    
    @After
    public void cleanup() throws Exception {
        for (BaseArtifactType createdArtifact : createdArtifacts) {
            binding.delete(createdArtifact);
        }
        createdArtifacts.clear();
    }

}
