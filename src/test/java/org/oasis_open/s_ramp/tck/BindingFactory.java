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

import java.util.Arrays;
import java.util.List;

import org.oasis_open.s_ramp.tck.atom.AtomBinding;

/**
 * @author Brett Meyer
 */
public class BindingFactory {
    
    private static final List<Binding[]> bindings;
    
    static {
        bindings = Arrays.asList(new Binding[][] { { new AtomBinding() } });
    }
    
    // For use with JUnit's @Parameters
    public static List<Binding[]> getBindings() {
        return bindings;
    }
}
