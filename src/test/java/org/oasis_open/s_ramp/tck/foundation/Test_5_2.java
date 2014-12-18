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
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.*;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.Process;
import org.oasis_open.docs.s_ramp.ns.s_ramp_v1.System;
import org.oasis_open.s_ramp.tck.ArtifactType;
import org.oasis_open.s_ramp.tck.Binding;

import static org.junit.Assert.assertEquals;

/**
 * @author Brett Meyer
 */
public class Test_5_2 extends AbstractFoundationTest {

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
        verifyArtifacts(binding.query("/s-ramp/xsd/AttributeDeclaration"), AttributeDeclaration.class);
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

    @Test
    public void testLogicalModels() throws Exception {
        // Just need some sort of derived artifact to target
        binding.upload(XsdDocument(), "/PO.xsd");
        BaseArtifactType derivedArtifact = binding.query("/s-ramp/xsd/SimpleTypeDeclaration").get(0);
        DerivedArtifactTarget derivedArtifactTarget = new DerivedArtifactTarget();
        derivedArtifactTarget.setArtifactType(DerivedArtifactEnum.SIMPLE_TYPE_DECLARATION);
        derivedArtifactTarget.setValue(derivedArtifact.getUuid());

        Actor actor = new Actor();
        actor = (Actor) binding.create(actor);
        ActorTarget actorTarget = new ActorTarget();
        actorTarget.setArtifactType(ActorEnum.ACTOR);
        actorTarget.setValue(actor.getUuid());

        Collaboration collaboration = new Collaboration();
        collaboration = (Collaboration) binding.create(collaboration);

        CollaborationProcess collaborationProcess = new CollaborationProcess();
        collaborationProcess = (CollaborationProcess) binding.create(collaborationProcess);

        Composition composition = new Composition();
        composition = (Composition) binding.create(composition);

        Choreography choreography = new Choreography();
        choreography = (Choreography) binding.create(choreography);

        ChoreographyProcess choreographyProcess = new ChoreographyProcess();
        choreographyProcess = (ChoreographyProcess) binding.create(choreographyProcess);

        Effect effect = new Effect();
        effect = (Effect) binding.create(effect);
        EffectTarget effectTarget = new EffectTarget();
        effectTarget.setArtifactType(EffectEnum.EFFECT);
        effectTarget.setValue(effect.getUuid());

        Element element = new Element();
        element = (Element) binding.create(element);
        ElementTarget elementTarget = new ElementTarget();
        elementTarget.setArtifactType(ElementEnum.ELEMENT);
        elementTarget.setValue(element.getUuid());

        Event event = new Event();
        event = (Event) binding.create(event);
        EventTarget eventTarget = new EventTarget();
        eventTarget.setArtifactType(EventEnum.EVENT);
        eventTarget.setValue(event.getUuid());

        InformationType informationType = new InformationType();
        informationType = (InformationType) binding.create(informationType);
        InformationTypeTarget informationTypeTarget = new InformationTypeTarget();
        informationTypeTarget.setArtifactType(InformationTypeEnum.INFORMATION_TYPE);
        informationTypeTarget.setValue(informationType.getUuid());

        Orchestration orchestration = new Orchestration();
        orchestration = (Orchestration) binding.create(orchestration);
        OrchestrationTarget orchestrationTarget = new OrchestrationTarget();
        orchestrationTarget.setArtifactType(OrchestrationEnum.ORCHESTRATION);
        orchestrationTarget.setValue(orchestration.getUuid());

        OrchestrationProcess orchestrationProcess = new OrchestrationProcess();
        orchestrationProcess = (OrchestrationProcess) binding.create(orchestrationProcess);
        OrchestrationProcessTarget orchestrationProcessTarget = new OrchestrationProcessTarget();
        orchestrationProcessTarget.setArtifactType(OrchestrationProcessEnum.ORCHESTRATION_PROCESS);
        orchestrationProcessTarget.setValue(orchestrationProcess.getUuid());

        Organization organization = new Organization();
        organization = (Organization) binding.create(organization);

        Policy policy = new Policy();
        policy = (Policy) binding.create(policy);
        PolicyTarget policyTarget = new PolicyTarget();
        policyTarget.setArtifactType(PolicyEnum.POLICY);
        policyTarget.setValue(policy.getUuid());

        PolicySubject policySubject = new PolicySubject();
        policySubject = (PolicySubject) binding.create(policySubject);
        PolicySubjectTarget policySubjectTarget = new PolicySubjectTarget();
        policySubjectTarget.setArtifactType(PolicySubjectEnum.POLICY_SUBJECT);
        policySubjectTarget.setValue(policySubject.getUuid());

        Process process = new Process();
        process = (Process) binding.create(process);

        Service service = new Service();
        service = (Service) binding.create(service);
        ServiceTarget serviceTarget = new ServiceTarget();
        serviceTarget.setArtifactType(ServiceEnum.SERVICE);
        serviceTarget.setValue(service.getUuid());

        ServiceComposition serviceComposition = new ServiceComposition();
        serviceComposition = (ServiceComposition) binding.create(serviceComposition);

        ServiceContract serviceContract = new ServiceContract();
        serviceContract = (ServiceContract) binding.create(serviceContract);
        ServiceContractTarget serviceContractTarget = new ServiceContractTarget();
        serviceContractTarget.setArtifactType(ServiceContractEnum.SERVICE_CONTRACT);
        serviceContractTarget.setValue(serviceContract.getUuid());

        ServiceEndpoint serviceEndpoint = new ServiceEndpoint();
        serviceEndpoint = (ServiceEndpoint) binding.create(serviceEndpoint);

        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance = (ServiceInstance) binding.create(serviceInstance);
        ServiceInstanceTarget serviceInstanceTarget = new ServiceInstanceTarget();
        serviceInstanceTarget.setArtifactType(ServiceInstanceEnum.SERVICE_INSTANCE);
        serviceInstanceTarget.setValue(serviceInstance.getUuid());

        ServiceInterface serviceInterface = new ServiceInterface();
        serviceInterface = (ServiceInterface) binding.create(serviceInterface);
        ServiceInterfaceTarget serviceInterfaceTarget = new ServiceInterfaceTarget();
        serviceInterfaceTarget.setArtifactType(ServiceInterfaceEnum.SERVICE_INTERFACE);
        serviceInterfaceTarget.setValue(serviceInterface.getUuid());

        ServiceOperation serviceOperation = new ServiceOperation();
        serviceOperation = (ServiceOperation) binding.create(serviceOperation);
        ServiceOperationTarget serviceOperationTarget = new ServiceOperationTarget();
        serviceOperationTarget.setArtifactType(ServiceOperationEnum.SERVICE_OPERATION);
        serviceOperationTarget.setValue(serviceOperation.getUuid());
        ServiceImplementationModelTarget serviceImplementationModelTarget = new ServiceImplementationModelTarget();
        serviceImplementationModelTarget.setArtifactType(ServiceImplementationModelEnum.SERVICE_OPERATION);
        serviceImplementationModelTarget.setValue(serviceOperation.getUuid());

        System system = new System();
        system = (System) binding.create(system);

        Task task = new Task();
        task = (Task) binding.create(task);
        TaskTarget taskTarget = new TaskTarget();
        taskTarget.setArtifactType(TaskEnum.TASK);
        taskTarget.setValue(task.getUuid());

        // Create all relationships after the fact, in order to guarantee full coverage without ordering problems.

        actor.getDoes().add(taskTarget);
        actor.getSetsPolicy().add(policyTarget);

        element.setDirectsOrchestration(orchestrationTarget);
        element.setDirectsOrchestrationProcess(orchestrationProcessTarget);
        element.getUses().add(elementTarget);
        element.getGenerates().add(eventTarget);
        element.getPerforms().add(serviceTarget);
        element.getRepresents().add(elementTarget);
        element.getRespondsTo().add(eventTarget);

        organization.getProvides().add(serviceImplementationModelTarget);

        policy.getAppliesTo().add(policySubjectTarget);

        service.setHasInstance(serviceInstanceTarget);
        service.getHasContract().add(serviceContractTarget);
        service.getHasInterface().add(serviceInterfaceTarget);
        service = (Service) binding.update(service);

        serviceContract.getInvolvesParty().add(actorTarget);
        serviceContract.getSpecifies().add(effectTarget);
        serviceContract = (ServiceContract) binding.update(serviceContract);

        serviceEndpoint.setEndpointDefinedBy(derivedArtifactTarget);

        // TODO: Doesn't make sense -- should be something other than ServiceInstanceTarget
        serviceInstance.getDescribedBy().add(serviceInstanceTarget);
        // TODO: Doesn't make sense -- should be something other than ServiceInstanceTarget
        serviceInstance.getUses().add(serviceInstanceTarget);
        serviceInstance = (ServiceInstance) binding.update(serviceInstance);

        serviceInterface.setHasOperation(serviceOperationTarget);
        serviceInterface.setInterfaceDefinedBy(derivedArtifactTarget);
        serviceInterface.getIsInterfaceOf().add(serviceTarget);
        serviceInterface.getHasInput().add(informationTypeTarget);
        serviceInterface.getHasOutput().add(informationTypeTarget);
        serviceInterface = (ServiceInterface) binding.update(serviceInterface);

        serviceOperation.setOperationDefinedBy(derivedArtifactTarget);
        serviceOperation = (ServiceOperation) binding.update(serviceOperation);

        // Basic verification
        verifyArtifact(actor);
        verifyArtifact(choreography);
        verifyArtifact(choreographyProcess);
        verifyArtifact(collaboration);
        verifyArtifact(collaborationProcess);
        verifyArtifact(composition);
        verifyArtifact(effect);
        verifyArtifact(element);
        verifyArtifact(event);
        verifyArtifact(informationType);
        verifyArtifact(orchestration);
        verifyArtifact(orchestrationProcess);
        verifyArtifact(organization);
        verifyArtifact(policy);
        verifyArtifact(policySubject);
        verifyArtifact(process);
        verifyArtifact(service);
        verifyArtifact(serviceComposition);
        verifyArtifact(serviceContract);
        verifyArtifact(serviceEndpoint);
        verifyArtifact(serviceInstance);
        verifyArtifact(serviceInterface);
        verifyArtifact(serviceOperation);
        verifyArtifact(system);
        verifyArtifact(task);

        // Verify all relationships
        assertEquals(task.getUuid(), actor.getDoes().get(0).getValue());
        assertEquals(policy.getUuid(), actor.getSetsPolicy().get(0).getValue());
        assertEquals(orchestration.getUuid(), element.getDirectsOrchestration().getValue());
        assertEquals(orchestrationProcess.getUuid(), element.getDirectsOrchestrationProcess().getValue());
        assertEquals(element.getUuid(), element.getUses().get(0).getValue());
        assertEquals(event.getUuid(), element.getGenerates().get(0).getValue());
        assertEquals(service.getUuid(), element.getPerforms().get(0).getValue());
        assertEquals(element.getUuid(), element.getRepresents().get(0).getValue());
        assertEquals(event.getUuid(), element.getRespondsTo().get(0).getValue());
        assertEquals(serviceOperation.getUuid(), organization.getProvides().get(0).getValue());
        assertEquals(policySubject.getUuid(), policy.getAppliesTo().get(0).getValue());
        assertEquals(serviceInstance.getUuid(), service.getHasInstance().getValue());
        assertEquals(serviceContract.getUuid(), service.getHasContract().get(0).getValue());
        assertEquals(serviceInterface.getUuid(), service.getHasInterface().get(0).getValue());
        assertEquals(actor.getUuid(), serviceContract.getInvolvesParty().get(0).getValue());
        assertEquals(effect.getUuid(), serviceContract.getSpecifies().get(0).getValue());
        assertEquals(derivedArtifact.getUuid(), serviceEndpoint.getEndpointDefinedBy().getValue());
        assertEquals(serviceInstance.getUuid(), serviceInstance.getDescribedBy().get(0).getValue());
        assertEquals(serviceInstance.getUuid(), serviceInstance.getUses().get(0).getValue());
        assertEquals(serviceOperation.getUuid(), serviceInterface.getHasOperation().getValue());
        assertEquals(service.getUuid(), serviceInterface.getIsInterfaceOf().get(0).getValue());
        assertEquals(informationType.getUuid(), serviceInterface.getHasInput().get(0).getValue());
        assertEquals(informationType.getUuid(), serviceInterface.getHasOutput().get(0).getValue());
        assertEquals(derivedArtifact.getUuid(), serviceOperation.getOperationDefinedBy().getValue());
    }
}
