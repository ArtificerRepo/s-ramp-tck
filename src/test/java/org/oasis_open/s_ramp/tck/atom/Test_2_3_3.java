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
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Invocation.Builder;

import org.jboss.resteasy.plugins.providers.atom.Category;
import org.jboss.resteasy.plugins.providers.atom.app.AppCategories;
import org.jboss.resteasy.plugins.providers.atom.app.AppCollection;
import org.jboss.resteasy.plugins.providers.atom.app.AppService;
import org.jboss.resteasy.plugins.providers.atom.app.AppWorkspace;
import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.StoredQuery;

/**
 * @author Brett Meyer
 */
public class Test_2_3_3 extends AbstractAtomTest {
    
    @Test
    public void testServiceDocument() throws Exception {
        // Needed for testStoredQueryWorkspace, but must happen *before* retrieving the service document.
        StoredQuery storedQuery1 = new StoredQuery();
        storedQuery1.setQueryExpression("/s-ramp/xsd/XsdDocument");
        storedQuery1.setQueryName("storedQuery1");
        binding.create(storedQuery1);
        StoredQuery storedQuery2 = new StoredQuery();
        storedQuery2.setQueryExpression("/s-ramp/xsd/XsdDocument");
        storedQuery2.setQueryName("storedQuery2");
        binding.create(storedQuery2);
        
        Builder clientRequest = binding.getClientRequest(AtomBinding.BASE_URL + "/s-ramp/servicedocument");
        AppService appService = clientRequest.get(AppService.class);
        assertNotNull(appService);
        testCoreWorkspace(appService.getWorkspace());
        testExtendedWorkspace(appService.getWorkspace());
        testPolicyWorkspace(appService.getWorkspace());
        testServiceImplementationWorkspace(appService.getWorkspace());
        testSOAPWSDLWorkspace(appService.getWorkspace());
        testSOAWorkspace(appService.getWorkspace());
        testStoredQueryWorkspace(appService.getWorkspace());
        testWSDLWorkspace(appService.getWorkspace());
        testXsdWorkspace(appService.getWorkspace());
    }
    
    private void testCoreWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "s-ramp/core", new String[] { "Document", "XmlDocument" });
        testWorkspace(workspaces, "s-ramp/core/Document", new String[] { "Document" });
        testWorkspace(workspaces, "s-ramp/core/XmlDocument", new String[] { "XmlDocument" });
    }
    
    private void testExtendedWorkspace(List<AppWorkspace> workspaces) {
        // TODO: May not be anything to test here, since there aren't any required terms
    }
    
    private void testPolicyWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "s-ramp/policy", new String[] { "PolicyDocument", "PolicyExpression",
                "PolicyAttachment" });
        testWorkspace(workspaces, "s-ramp/policy/PolicyDocument", new String[] { "PolicyDocument" });
        testWorkspace(workspaces, "s-ramp/policy/PolicyExpression", new String[] { "PolicyExpression" });
        testWorkspace(workspaces, "s-ramp/policy/PolicyAttachment", new String[] { "PolicyAttachment" });
    }
    
    private void testServiceImplementationWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "s-ramp/serviceImplementation", new String[] { "ServiceEndpoint", "ServiceInstance",
                "ServiceOperation" });
        testWorkspace(workspaces, "s-ramp/serviceImplementation/ServiceEndpoint", new String[] { "ServiceEndpoint" });
        testWorkspace(workspaces, "s-ramp/serviceImplementation/ServiceInstance", new String[] { "ServiceInstance" });
        testWorkspace(workspaces, "s-ramp/serviceImplementation/ServiceOperation", new String[] { "ServiceOperation" });
    }
    
    private void testSOAPWSDLWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "s-ramp/soapWsdl", new String[] { "SoapAddress", "SoapBinding" });
        testWorkspace(workspaces, "s-ramp/soapWsdl/SoapAddress", new String[] { "SoapAddress" });
        testWorkspace(workspaces, "s-ramp/soapWsdl/SoapBinding", new String[] { "SoapBinding" });
    }
    
    private void testSOAWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "/s-ramp/soa", new String[] { "Effect", "Event", "InformationType", "Policy",
                "PolicySubject", "Element", "Actor", "Organization", "Service", "System", "Composition", "Choreography",
                "Collaboration", "Orchestration", "Process", "ChoreographyProcess", "CollaborationProcess",
                "OrchestrationProcess", "ServiceComposition", "Task", "ServiceContract", "ServiceInterface" });
        testWorkspace(workspaces, "/s-ramp/soa/Effect", new String[] { "Effect" });
        testWorkspace(workspaces, "/s-ramp/soa/Event", new String[] { "Event" });
        testWorkspace(workspaces, "/s-ramp/soa/InformationType", new String[] { "InformationType" });
        testWorkspace(workspaces, "/s-ramp/soa/Policy", new String[] { "Policy" });
        testWorkspace(workspaces, "/s-ramp/soa/PolicySubject", new String[] { "PolicySubject" });
        testWorkspace(workspaces, "/s-ramp/soa/Element", new String[] { "Element" });
        testWorkspace(workspaces, "/s-ramp/soa/Actor", new String[] { "Actor" });
        testWorkspace(workspaces, "/s-ramp/soa/Organization", new String[] { "Organization" });
        testWorkspace(workspaces, "/s-ramp/soa/Service", new String[] { "Service" });
        testWorkspace(workspaces, "/s-ramp/soa/System", new String[] { "System" });
        testWorkspace(workspaces, "/s-ramp/soa/Composition", new String[] { "Composition" });
        testWorkspace(workspaces, "/s-ramp/soa/Choreography", new String[] { "Choreography" });
        testWorkspace(workspaces, "/s-ramp/soa/Collaboration", new String[] { "Collaboration" });
        testWorkspace(workspaces, "/s-ramp/soa/Orchestration", new String[] { "Orchestration" });
        testWorkspace(workspaces, "/s-ramp/soa/Process", new String[] { "Process" });
        testWorkspace(workspaces, "/s-ramp/soa/ChoreographyProcess", new String[] { "ChoreographyProcess" });
        testWorkspace(workspaces, "/s-ramp/soa/CollaborationProcess", new String[] { "CollaborationProcess" });
        testWorkspace(workspaces, "/s-ramp/soa/OrchestrationProcess", new String[] { "OrchestrationProcess" });
        testWorkspace(workspaces, "/s-ramp/soa/ServiceComposition", new String[] { "ServiceComposition" });
        testWorkspace(workspaces, "/s-ramp/soa/Task", new String[] { "Task" });
        testWorkspace(workspaces, "/s-ramp/soa/ServiceContract", new String[] { "ServiceContract" });
        testWorkspace(workspaces, "/s-ramp/soa/ServiceInterface", new String[] { "ServiceInterface" });
    }
    
    private void testStoredQueryWorkspace(List<AppWorkspace> workspaces) throws Exception {
        testWorkspace(workspaces, "s-ramp/query", new String[] { "query" });
        testWorkspace(workspaces, "s-ramp/query/storedQuery1", new String[] { "query" });
        testWorkspace(workspaces, "s-ramp/query/storedQuery2", new String[] { "query" });
    }
    
    private void testWSDLWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "s-ramp/wsdl", new String[] { "WsdlDocument", "WsdlService", "Port", "WsdlExtension",
                "Part", "Message", "Fault", "PortType", "Operation", "OperationInput", "OperationOutput", "Binding",
                "BindingOperation", "BindingOperationInput", "BindingOperationOutput", "BindingOperationFault" });
        testWorkspace(workspaces, "s-ramp/wsdl/WsdlDocument", new String[] { "WsdlDocument" });
        testWorkspace(workspaces, "s-ramp/wsdl/WsdlService", new String[] { "WsdlService" });
        testWorkspace(workspaces, "s-ramp/wsdl/Port", new String[] { "Port" });
        testWorkspace(workspaces, "s-ramp/wsdl/WsdlExtension", new String[] { "WsdlExtension" });
        testWorkspace(workspaces, "s-ramp/wsdl/Part", new String[] { "Part" });
        testWorkspace(workspaces, "s-ramp/wsdl/Message", new String[] { "Message" });
        testWorkspace(workspaces, "s-ramp/wsdl/Fault", new String[] { "Fault" });
        testWorkspace(workspaces, "s-ramp/wsdl/PortType", new String[] { "PortType" });
        testWorkspace(workspaces, "s-ramp/wsdl/Operation", new String[] { "Operation" });
        testWorkspace(workspaces, "s-ramp/wsdl/OperationInput", new String[] { "OperationInput" });
        testWorkspace(workspaces, "s-ramp/wsdl/OperationOutput", new String[] { "OperationOutput" });
        testWorkspace(workspaces, "s-ramp/wsdl/Binding", new String[] { "Binding" });
        testWorkspace(workspaces, "s-ramp/wsdl/BindingOperation", new String[] { "BindingOperation" });
        testWorkspace(workspaces, "s-ramp/wsdl/BindingOperationInput", new String[] { "BindingOperationInput" });
        testWorkspace(workspaces, "s-ramp/wsdl/BindingOperationOutput", new String[] { "BindingOperationOutput" });
        testWorkspace(workspaces, "s-ramp/wsdl/BindingOperationFault", new String[] { "BindingOperationFault" });
    }
    
    private void testXsdWorkspace(List<AppWorkspace> workspaces) {
        testWorkspace(workspaces, "s-ramp/xsd", new String[] { "XsdDocument", "AttributeDeclaration", "ElementDeclaration",
                "SimpleTypeDeclaration", "ComplexTypeDeclaration" });
        testWorkspace(workspaces, "s-ramp/xsd/XsdDocument", new String[] { "XsdDocument" });
        testWorkspace(workspaces, "s-ramp/xsd/AttributeDeclaration", new String[] { "AttributeDeclaration" });
        testWorkspace(workspaces, "s-ramp/xsd/ElementDeclaration", new String[] { "ElementDeclaration" });
        testWorkspace(workspaces, "s-ramp/xsd/SimpleTypeDeclaration", new String[] { "SimpleTypeDeclaration" });
        testWorkspace(workspaces, "s-ramp/xsd/ComplexTypeDeclaration", new String[] { "ComplexTypeDeclaration" });
    }
    
    private void testWorkspace(List<AppWorkspace> workspaces, String collectionHref, String[] expectedTerms) {
        List<String> expectedTermsList = Arrays.asList(expectedTerms);
        for (AppWorkspace workspace : workspaces) {
            for (AppCollection collection : workspace.getCollection()) {
                if (collection.getHref().contains(collectionHref)) {
                    for (AppCategories categories : collection.getCategories()) {
                        int count = 0;
                        for (Category category : categories.getCategory()) {
                            // Note: Since specific titles, etc. aren't required by the spec, testing against the category
                            // schemes and terms is the only option.
                            assertEquals("urn:x-s-ramp:2013:type", category.getScheme().toString());
                            if (expectedTermsList.contains(category.getTerm())) {
                                count++;
                            }
                        }
                        if (count == expectedTerms.length) {
                            // found them all -- simply return
                            return;
                        }
                    }
                }
            }
        }
        fail("Either failed to find the " + collectionHref + " workspace, or the workspace did not contain the expected terms");
    }
}
