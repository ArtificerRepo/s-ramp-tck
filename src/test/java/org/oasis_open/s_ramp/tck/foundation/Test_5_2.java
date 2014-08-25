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

import org.junit.Test;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BaseArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BindingOperation;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BindingOperationFault;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BindingOperationInput;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.BindingOperationOutput;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ComplexTypeDeclaration;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Document;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ElementDeclaration;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedArtifactType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.ExtendedDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Fault;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Message;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Operation;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.OperationInput;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.OperationOutput;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Part;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.PolicyDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Port;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.PortType;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.SimpleTypeDeclaration;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.SoapAddress;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.SoapBinding;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.WsdlDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.WsdlService;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XmlDocument;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.XsdDocument;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

/**
 * @author Brett Meyer
 */
public class Test_5_2 extends CoreModelTest {

    public Test_5_2(Binding binding) {
        super(binding);
    }
    
    @Test
    public void testCoreModel() throws Exception {
        BaseArtifactType artifact = binding.upload(Document(), "/foo.txt");
        verifyArtifact(artifact, Document.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.Document());
        verifyArtifact(artifact, Document.class);
        
        artifact = binding.upload(XmlDocument(), "/PO.xml");
        verifyArtifact(artifact, XmlDocument.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.XmlDocument());
        verifyArtifact(artifact, XmlDocument.class);
    }
    
    @Test
    public void testExtendedModel() throws Exception {
        BaseArtifactType artifact = binding.create(ExtendedArtifactType("FooType"));
        verifyArtifact(artifact, ExtendedArtifactType.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.ExtendedArtifactType("FooType", false));
        verifyArtifact(artifact, ExtendedArtifactType.class);
        
        artifact = binding.upload(ExtendedDocument("FooType"), "/foo.txt");
        verifyArtifact(artifact, ExtendedDocument.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.ExtendedDocument("FooType"));
        verifyArtifact(artifact, ExtendedDocument.class);
    }
    
    @Test
    public void testXsdModel() throws Exception {
        BaseArtifactType artifact = binding.upload(XsdDocument(), "/PO.xsd");
        verifyArtifact(artifact, XsdDocument.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.XsdDocument());
        verifyArtifact(artifact, XsdDocument.class);

        verifyArtifacts(binding.query("/s-ramp/xsd/ElementDeclaration"), ElementDeclaration.class);
        // TODO FAILURE: SRAMP-550
//        verifyArtifacts(binding.query("/s-ramp/xsd/AttributeDeclaration"), AttributeDeclaration.class);
        verifyArtifacts(binding.query("/s-ramp/xsd/ComplexTypeDeclaration"), ComplexTypeDeclaration.class);
        verifyArtifacts(binding.query("/s-ramp/xsd/SimpleTypeDeclaration"), SimpleTypeDeclaration.class);
    }
    
    @Test
    public void testWsdlModel() throws Exception {
        BaseArtifactType artifact = binding.upload(WsdlDocument(), "/deriver.wsdl");
        verifyArtifact(artifact, WsdlDocument.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.WsdlDocument());
        verifyArtifact(artifact, WsdlDocument.class);

        verifyArtifacts(binding.query("/s-ramp/wsdl/WsdlService"), WsdlService.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/Port"), Port.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/Binding"), org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Binding.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/PortType"), PortType.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/BindingOperation"), BindingOperation.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/BindingOperationInput"), BindingOperationInput.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/BindingOperationFault"), BindingOperationFault.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/BindingOperationOutput"), BindingOperationOutput.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/Operation"), Operation.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/OperationInput"), OperationInput.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/OperationOutput"), OperationOutput.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/Fault"), Fault.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/Message"), Message.class);
        verifyArtifacts(binding.query("/s-ramp/wsdl/Part"), Part.class);
        
        // include the SOAP WSDL Model
        verifyArtifacts(binding.query("/s-ramp/soapwsdl/SoapAddress"), SoapAddress.class);
        verifyArtifacts(binding.query("/s-ramp/soapwsdl/SoapBinding"), SoapBinding.class);
    }
    
    @Test
    public void testSoapWsdlModel() throws Exception {
        BaseArtifactType artifact = binding.upload(WsdlDocument(), "/deriver.wsdl");
        verifyArtifact(artifact, WsdlDocument.class);

        verifyArtifacts(binding.query("/s-ramp/soapwsdl/SoapAddress"), SoapAddress.class);
        verifyArtifacts(binding.query("/s-ramp/soapwsdl/SoapBinding"), SoapBinding.class);
    }
    
    @Test
    public void testPolicyModel() throws Exception {
        BaseArtifactType artifact = binding.upload(PolicyDocument(), "/policy.xml");
        verifyArtifact(artifact, PolicyDocument.class);
        artifact = binding.get(artifact.getUuid(), ArtifactType.PolicyDocument());
        verifyArtifact(artifact, PolicyDocument.class);
        
        artifact = binding.upload(WsdlDocument(), "/deriver.wsdl");
        verifyArtifact(artifact, WsdlDocument.class);

        // TODO FAILURE: SRAMP-89
//        verifyArtifacts(binding.query("/s-ramp/policy/PolicyAttachment"), PolicyAttachment.class);
//        verifyArtifacts(binding.query("/s-ramp/policy/PolicyExpression"), PolicyExpression.class);
    }
    
    // TODO: SOA Model (SRAMP-167)
    // TODO: Service Implementation Model (SRAMP-167)
}
