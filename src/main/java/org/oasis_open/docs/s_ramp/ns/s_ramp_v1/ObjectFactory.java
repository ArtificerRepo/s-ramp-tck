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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.18 at 03:02:22 PM EST 
//


package org.oasis_open.docs.s_ramp.ns.s_ramp_v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.oasis_open.docs.s_ramp.ns.s_ramp_v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RelationshipType_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "relationshipType");
    private final static QName _QueryExpression_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "queryExpression");
    private final static QName _TargetId_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "targetId");
    private final static QName _ClassifiedBy_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "classifiedBy");
    private final static QName _DerivedArtifactType_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "DerivedArtifactType");
    private final static QName _SourceId_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "sourceId");
    private final static QName _PropertyName_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "propertyName");
    private final static QName _PropertyValue_QNAME = new QName("http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", "propertyValue");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.oasis_open.docs.s_ramp.ns.s_ramp_v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RelationshipData }
     *
     */
    public RelationshipData createRelationshipData() {
        return new RelationshipData();
    }

    /**
     * Create an instance of {@link Error }
     *
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link ErrorResponse }
     *
     */
    public ErrorResponse createErrorResponse() {
        return new ErrorResponse();
    }

    /**
     * Create an instance of {@link PropertyData }
     *
     */
    public PropertyData createPropertyData() {
        return new PropertyData();
    }

    /**
     * Create an instance of {@link Property }
     *
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link StoredQueryData }
     *
     */
    public StoredQueryData createStoredQueryData() {
        return new StoredQueryData();
    }

    /**
     * Create an instance of {@link Relationship }
     *
     */
    public Relationship createRelationship() {
        return new Relationship();
    }

    /**
     * Create an instance of {@link Target }
     *
     */
    public Target createTarget() {
        return new Target();
    }

    /**
     * Create an instance of {@link Artifact }
     *
     */
    public Artifact createArtifact() {
        return new Artifact();
    }

    /**
     * Create an instance of {@link Document }
     *
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link XmlDocument }
     *
     */
    public XmlDocument createXmlDocument() {
        return new XmlDocument();
    }

    /**
     * Create an instance of {@link ExtendedArtifactType }
     *
     */
    public ExtendedArtifactType createExtendedArtifactType() {
        return new ExtendedArtifactType();
    }

    /**
     * Create an instance of {@link ExtendedDocument }
     *
     */
    public ExtendedDocument createExtendedDocument() {
        return new ExtendedDocument();
    }

    /**
     * Create an instance of {@link Organization }
     *
     */
    public Organization createOrganization() {
        return new Organization();
    }

    /**
     * Create an instance of {@link ServiceEndpoint }
     *
     */
    public ServiceEndpoint createServiceEndpoint() {
        return new ServiceEndpoint();
    }

    /**
     * Create an instance of {@link ServiceInstance }
     *
     */
    public ServiceInstance createServiceInstance() {
        return new ServiceInstance();
    }

    /**
     * Create an instance of {@link ServiceOperation }
     *
     */
    public ServiceOperation createServiceOperation() {
        return new ServiceOperation();
    }

    /**
     * Create an instance of {@link Actor }
     *
     */
    public Actor createActor() {
        return new Actor();
    }

    /**
     * Create an instance of {@link Choreography }
     *
     */
    public Choreography createChoreography() {
        return new Choreography();
    }

    /**
     * Create an instance of {@link ChoreographyProcess }
     *
     */
    public ChoreographyProcess createChoreographyProcess() {
        return new ChoreographyProcess();
    }

    /**
     * Create an instance of {@link Collaboration }
     *
     */
    public Collaboration createCollaboration() {
        return new Collaboration();
    }

    /**
     * Create an instance of {@link CollaborationProcess }
     *
     */
    public CollaborationProcess createCollaborationProcess() {
        return new CollaborationProcess();
    }

    /**
     * Create an instance of {@link Composition }
     *
     */
    public Composition createComposition() {
        return new Composition();
    }

    /**
     * Create an instance of {@link Effect }
     *
     */
    public Effect createEffect() {
        return new Effect();
    }

    /**
     * Create an instance of {@link Element }
     *
     */
    public Element createElement() {
        return new Element();
    }

    /**
     * Create an instance of {@link Event }
     *
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link InformationType }
     *
     */
    public InformationType createInformationType() {
        return new InformationType();
    }

    /**
     * Create an instance of {@link Orchestration }
     *
     */
    public Orchestration createOrchestration() {
        return new Orchestration();
    }

    /**
     * Create an instance of {@link OrchestrationProcess }
     *
     */
    public OrchestrationProcess createOrchestrationProcess() {
        return new OrchestrationProcess();
    }

    /**
     * Create an instance of {@link Policy }
     *
     */
    public Policy createPolicy() {
        return new Policy();
    }

    /**
     * Create an instance of {@link PolicySubject }
     *
     */
    public PolicySubject createPolicySubject() {
        return new PolicySubject();
    }

    /**
     * Create an instance of {@link Process }
     *
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link Service }
     *
     */
    public Service createService() {
        return new Service();
    }

    /**
     * Create an instance of {@link ServiceContract }
     *
     */
    public ServiceContract createServiceContract() {
        return new ServiceContract();
    }

    /**
     * Create an instance of {@link ServiceComposition }
     *
     */
    public ServiceComposition createServiceComposition() {
        return new ServiceComposition();
    }

    /**
     * Create an instance of {@link ServiceInterface }
     *
     */
    public ServiceInterface createServiceInterface() {
        return new ServiceInterface();
    }

    /**
     * Create an instance of {@link System }
     *
     */
    public System createSystem() {
        return new System();
    }

    /**
     * Create an instance of {@link Task }
     *
     */
    public Task createTask() {
        return new Task();
    }

    /**
     * Create an instance of {@link PolicyAttachment }
     *
     */
    public PolicyAttachment createPolicyAttachment() {
        return new PolicyAttachment();
    }

    /**
     * Create an instance of {@link PolicyExpression }
     *
     */
    public PolicyExpression createPolicyExpression() {
        return new PolicyExpression();
    }

    /**
     * Create an instance of {@link PolicyDocument }
     *
     */
    public PolicyDocument createPolicyDocument() {
        return new PolicyDocument();
    }

    /**
     * Create an instance of {@link XsdDocument }
     *
     */
    public XsdDocument createXsdDocument() {
        return new XsdDocument();
    }

    /**
     * Create an instance of {@link AttributeDeclaration }
     *
     */
    public AttributeDeclaration createAttributeDeclaration() {
        return new AttributeDeclaration();
    }

    /**
     * Create an instance of {@link ElementDeclaration }
     *
     */
    public ElementDeclaration createElementDeclaration() {
        return new ElementDeclaration();
    }

    /**
     * Create an instance of {@link ComplexTypeDeclaration }
     *
     */
    public ComplexTypeDeclaration createComplexTypeDeclaration() {
        return new ComplexTypeDeclaration();
    }

    /**
     * Create an instance of {@link SimpleTypeDeclaration }
     *
     */
    public SimpleTypeDeclaration createSimpleTypeDeclaration() {
        return new SimpleTypeDeclaration();
    }

    /**
     * Create an instance of {@link WsdlDocument }
     *
     */
    public WsdlDocument createWsdlDocument() {
        return new WsdlDocument();
    }

    /**
     * Create an instance of {@link WsdlService }
     *
     */
    public WsdlService createWsdlService() {
        return new WsdlService();
    }

    /**
     * Create an instance of {@link Port }
     *
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link WsdlExtension }
     *
     */
    public WsdlExtension createWsdlExtension() {
        return new WsdlExtension();
    }

    /**
     * Create an instance of {@link Part }
     *
     */
    public Part createPart() {
        return new Part();
    }

    /**
     * Create an instance of {@link Message }
     *
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link Fault }
     *
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link PortType }
     *
     */
    public PortType createPortType() {
        return new PortType();
    }

    /**
     * Create an instance of {@link Operation }
     *
     */
    public Operation createOperation() {
        return new Operation();
    }

    /**
     * Create an instance of {@link OperationInput }
     *
     */
    public OperationInput createOperationInput() {
        return new OperationInput();
    }

    /**
     * Create an instance of {@link OperationOutput }
     *
     */
    public OperationOutput createOperationOutput() {
        return new OperationOutput();
    }

    /**
     * Create an instance of {@link Binding }
     *
     */
    public Binding createBinding() {
        return new Binding();
    }

    /**
     * Create an instance of {@link BindingOperation }
     *
     */
    public BindingOperation createBindingOperation() {
        return new BindingOperation();
    }

    /**
     * Create an instance of {@link BindingOperationInput }
     *
     */
    public BindingOperationInput createBindingOperationInput() {
        return new BindingOperationInput();
    }

    /**
     * Create an instance of {@link BindingOperationOutput }
     *
     */
    public BindingOperationOutput createBindingOperationOutput() {
        return new BindingOperationOutput();
    }

    /**
     * Create an instance of {@link BindingOperationFault }
     *
     */
    public BindingOperationFault createBindingOperationFault() {
        return new BindingOperationFault();
    }

    /**
     * Create an instance of {@link SoapAddress }
     *
     */
    public SoapAddress createSoapAddress() {
        return new SoapAddress();
    }

    /**
     * Create an instance of {@link SoapBinding }
     *
     */
    public SoapBinding createSoapBinding() {
        return new SoapBinding();
    }

    /**
     * Create an instance of {@link StoredQuery }
     *
     */
    public StoredQuery createStoredQuery() {
        return new StoredQuery();
    }

    /**
     * Create an instance of {@link RelationshipTypeData }
     *
     */
    public RelationshipTypeData createRelationshipTypeData() {
        return new RelationshipTypeData();
    }

    /**
     * Create an instance of {@link ClassificationData }
     *
     */
    public ClassificationData createClassificationData() {
        return new ClassificationData();
    }

    /**
     * Create an instance of {@link OrchestrationProcessTarget }
     *
     */
    public OrchestrationProcessTarget createOrchestrationProcessTarget() {
        return new OrchestrationProcessTarget();
    }

    /**
     * Create an instance of {@link ElementTarget }
     *
     */
    public ElementTarget createElementTarget() {
        return new ElementTarget();
    }

    /**
     * Create an instance of {@link PolicySubjectTarget }
     *
     */
    public PolicySubjectTarget createPolicySubjectTarget() {
        return new PolicySubjectTarget();
    }

    /**
     * Create an instance of {@link BindingOperationOutputTarget }
     *
     */
    public BindingOperationOutputTarget createBindingOperationOutputTarget() {
        return new BindingOperationOutputTarget();
    }

    /**
     * Create an instance of {@link ServiceOperationTarget }
     *
     */
    public ServiceOperationTarget createServiceOperationTarget() {
        return new ServiceOperationTarget();
    }

    /**
     * Create an instance of {@link BindingOperationInputTarget }
     *
     */
    public BindingOperationInputTarget createBindingOperationInputTarget() {
        return new BindingOperationInputTarget();
    }

    /**
     * Create an instance of {@link OperationTarget }
     *
     */
    public OperationTarget createOperationTarget() {
        return new OperationTarget();
    }

    /**
     * Create an instance of {@link ActorTarget }
     *
     */
    public ActorTarget createActorTarget() {
        return new ActorTarget();
    }

    /**
     * Create an instance of {@link InformationTypeTarget }
     *
     */
    public InformationTypeTarget createInformationTypeTarget() {
        return new InformationTypeTarget();
    }

    /**
     * Create an instance of {@link ServiceContractTarget }
     *
     */
    public ServiceContractTarget createServiceContractTarget() {
        return new ServiceContractTarget();
    }

    /**
     * Create an instance of {@link DerivedArtifactTarget }
     *
     */
    public DerivedArtifactTarget createDerivedArtifactTarget() {
        return new DerivedArtifactTarget();
    }

    /**
     * Create an instance of {@link PortTarget }
     *
     */
    public PortTarget createPortTarget() {
        return new PortTarget();
    }

    /**
     * Create an instance of {@link EventTarget }
     *
     */
    public EventTarget createEventTarget() {
        return new EventTarget();
    }

    /**
     * Create an instance of {@link PolicyTarget }
     *
     */
    public PolicyTarget createPolicyTarget() {
        return new PolicyTarget();
    }

    /**
     * Create an instance of {@link OrchestrationTarget }
     *
     */
    public OrchestrationTarget createOrchestrationTarget() {
        return new OrchestrationTarget();
    }

    /**
     * Create an instance of {@link ServiceImplementationModelTarget }
     *
     */
    public ServiceImplementationModelTarget createServiceImplementationModelTarget() {
        return new ServiceImplementationModelTarget();
    }

    /**
     * Create an instance of {@link XsdDocumentTarget }
     *
     */
    public XsdDocumentTarget createXsdDocumentTarget() {
        return new XsdDocumentTarget();
    }

    /**
     * Create an instance of {@link PartTarget }
     *
     */
    public PartTarget createPartTarget() {
        return new PartTarget();
    }

    /**
     * Create an instance of {@link ExtensionType }
     *
     */
    public ExtensionType createExtensionType() {
        return new ExtensionType();
    }

    /**
     * Create an instance of {@link OperationOutputTarget }
     *
     */
    public OperationOutputTarget createOperationOutputTarget() {
        return new OperationOutputTarget();
    }

    /**
     * Create an instance of {@link WsdlDocumentTarget }
     *
     */
    public WsdlDocumentTarget createWsdlDocumentTarget() {
        return new WsdlDocumentTarget();
    }

    /**
     * Create an instance of {@link OperationInputTarget }
     *
     */
    public OperationInputTarget createOperationInputTarget() {
        return new OperationInputTarget();
    }

    /**
     * Create an instance of {@link BindingOperationTarget }
     *
     */
    public BindingOperationTarget createBindingOperationTarget() {
        return new BindingOperationTarget();
    }

    /**
     * Create an instance of {@link BaseArtifactTarget }
     *
     */
    public BaseArtifactTarget createBaseArtifactTarget() {
        return new BaseArtifactTarget();
    }

    /**
     * Create an instance of {@link PolicyExpressionTarget }
     *
     */
    public PolicyExpressionTarget createPolicyExpressionTarget() {
        return new PolicyExpressionTarget();
    }

    /**
     * Create an instance of {@link EffectTarget }
     *
     */
    public EffectTarget createEffectTarget() {
        return new EffectTarget();
    }

    /**
     * Create an instance of {@link XsdTypeTarget }
     *
     */
    public XsdTypeTarget createXsdTypeTarget() {
        return new XsdTypeTarget();
    }

    /**
     * Create an instance of {@link ElementDeclarationTarget }
     *
     */
    public ElementDeclarationTarget createElementDeclarationTarget() {
        return new ElementDeclarationTarget();
    }

    /**
     * Create an instance of {@link DocumentArtifactTarget }
     *
     */
    public DocumentArtifactTarget createDocumentArtifactTarget() {
        return new DocumentArtifactTarget();
    }

    /**
     * Create an instance of {@link ServiceInterfaceTarget }
     *
     */
    public ServiceInterfaceTarget createServiceInterfaceTarget() {
        return new ServiceInterfaceTarget();
    }

    /**
     * Create an instance of {@link BindingTarget }
     *
     */
    public BindingTarget createBindingTarget() {
        return new BindingTarget();
    }

    /**
     * Create an instance of {@link WsdlExtensionTarget }
     *
     */
    public WsdlExtensionTarget createWsdlExtensionTarget() {
        return new WsdlExtensionTarget();
    }

    /**
     * Create an instance of {@link ServiceInstanceTarget }
     *
     */
    public ServiceInstanceTarget createServiceInstanceTarget() {
        return new ServiceInstanceTarget();
    }

    /**
     * Create an instance of {@link ServiceTarget }
     *
     */
    public ServiceTarget createServiceTarget() {
        return new ServiceTarget();
    }

    /**
     * Create an instance of {@link MessageTarget }
     *
     */
    public MessageTarget createMessageTarget() {
        return new MessageTarget();
    }

    /**
     * Create an instance of {@link TaskTarget }
     *
     */
    public TaskTarget createTaskTarget() {
        return new TaskTarget();
    }

    /**
     * Create an instance of {@link FaultTarget }
     *
     */
    public FaultTarget createFaultTarget() {
        return new FaultTarget();
    }

    /**
     * Create an instance of {@link BindingOperationFaultTarget }
     *
     */
    public BindingOperationFaultTarget createBindingOperationFaultTarget() {
        return new BindingOperationFaultTarget();
    }

    /**
     * Create an instance of {@link PortTypeTarget }
     *
     */
    public PortTypeTarget createPortTypeTarget() {
        return new PortTypeTarget();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "relationshipType")
    public JAXBElement<String> createRelationshipType(String value) {
        return new JAXBElement<String>(_RelationshipType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "queryExpression")
    public JAXBElement<String> createQueryExpression(String value) {
        return new JAXBElement<String>(_QueryExpression_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "targetId")
    public JAXBElement<String> createTargetId(String value) {
        return new JAXBElement<String>(_TargetId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "classifiedBy")
    public JAXBElement<String> createClassifiedBy(String value) {
        return new JAXBElement<String>(_ClassifiedBy_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link DerivedArtifactType }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "DerivedArtifactType")
    public JAXBElement<DerivedArtifactType> createDerivedArtifactType(DerivedArtifactType value) {
        return new JAXBElement<DerivedArtifactType>(_DerivedArtifactType_QNAME, DerivedArtifactType.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "sourceId")
    public JAXBElement<String> createSourceId(String value) {
        return new JAXBElement<String>(_SourceId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "propertyName")
    public JAXBElement<String> createPropertyName(String value) {
        return new JAXBElement<String>(_PropertyName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/s-ramp/ns/s-ramp-v1.0", name = "propertyValue")
    public JAXBElement<String> createPropertyValue(String value) {
        return new JAXBElement<String>(_PropertyValue_QNAME, String.class, null, value);
    }

}
