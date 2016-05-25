
package br.com.esec.mss.ap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.esec.mss.ap package. 
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

    private final static QName _DateType_QNAME = new QName("http://esec.com.br/mss/ap", "DateType");
    private final static QName _AlgorithmFilterType_QNAME = new QName("http://esec.com.br/mss/ap", "AlgorithmFilterType");
    private final static QName _OwnerFilterType_QNAME = new QName("http://esec.com.br/mss/ap", "OwnerFilterType");
    private final static QName _ForceHardwareFilterType_QNAME = new QName("http://esec.com.br/mss/ap", "ForceHardwareFilterType");
    private final static QName _CertificateFiltersType_QNAME = new QName("http://esec.com.br/mss/ap", "certificateFiltersType");
    private final static QName _StringType_QNAME = new QName("http://esec.com.br/mss/ap", "StringType");
    private final static QName _FaultDetail_QNAME = new QName("http://esec.com.br/mss/ap", "FaultDetail");
    private final static QName _DoubleType_QNAME = new QName("http://esec.com.br/mss/ap", "DoubleType");
    private final static QName _HsmCertificateFilterType_QNAME = new QName("http://esec.com.br/mss/ap", "HsmCertificateFilterType");
    private final static QName _IntegerType_QNAME = new QName("http://esec.com.br/mss/ap", "IntegerType");
    private final static QName _TrustChainFilterType_QNAME = new QName("http://esec.com.br/mss/ap", "TrustChainFilterType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.esec.mss.ap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IntegerType }
     * 
     */
    public IntegerType createIntegerType() {
        return new IntegerType();
    }

    /**
     * Create an instance of {@link TrustChainFilterType }
     * 
     */
    public TrustChainFilterType createTrustChainFilterType() {
        return new TrustChainFilterType();
    }

    /**
     * Create an instance of {@link StringType }
     * 
     */
    public StringType createStringType() {
        return new StringType();
    }

    /**
     * Create an instance of {@link DoubleType }
     * 
     */
    public DoubleType createDoubleType() {
        return new DoubleType();
    }

    /**
     * Create an instance of {@link HsmCertificateFilterType }
     * 
     */
    public HsmCertificateFilterType createHsmCertificateFilterType() {
        return new HsmCertificateFilterType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link AlgorithmFilterType }
     * 
     */
    public AlgorithmFilterType createAlgorithmFilterType() {
        return new AlgorithmFilterType();
    }

    /**
     * Create an instance of {@link OwnerFilterType }
     * 
     */
    public OwnerFilterType createOwnerFilterType() {
        return new OwnerFilterType();
    }

    /**
     * Create an instance of {@link DateType }
     * 
     */
    public DateType createDateType() {
        return new DateType();
    }

    /**
     * Create an instance of {@link ForceHardwareFilterType }
     * 
     */
    public ForceHardwareFilterType createForceHardwareFilterType() {
        return new ForceHardwareFilterType();
    }

    /**
     * Create an instance of {@link CertificateFiltersType }
     * 
     */
    public CertificateFiltersType createCertificateFiltersType() {
        return new CertificateFiltersType();
    }

    /**
     * Create an instance of {@link SignatureTemplateReqType }
     * 
     */
    public SignatureTemplateReqType createSignatureTemplateReqType() {
        return new SignatureTemplateReqType();
    }

    /**
     * Create an instance of {@link DocumentSignatureInfoType }
     * 
     */
    public DocumentSignatureInfoType createDocumentSignatureInfoType() {
        return new DocumentSignatureInfoType();
    }

    /**
     * Create an instance of {@link BatchSignatureComplexDocumentReqType }
     * 
     */
    public BatchSignatureComplexDocumentReqType createBatchSignatureComplexDocumentReqType() {
        return new BatchSignatureComplexDocumentReqType();
    }

    /**
     * Create an instance of {@link CertificateInfoType }
     * 
     */
    public CertificateInfoType createCertificateInfoType() {
        return new CertificateInfoType();
    }

    /**
     * Create an instance of {@link BatchSignatureComplexDocumentRespType }
     * 
     */
    public BatchSignatureComplexDocumentRespType createBatchSignatureComplexDocumentRespType() {
        return new BatchSignatureComplexDocumentRespType();
    }

    /**
     * Create an instance of {@link SignatureSimpleDocumentReqType }
     * 
     */
    public SignatureSimpleDocumentReqType createSignatureSimpleDocumentReqType() {
        return new SignatureSimpleDocumentReqType();
    }

    /**
     * Create an instance of {@link SignatureInfoType }
     * 
     */
    public SignatureInfoType createSignatureInfoType() {
        return new SignatureInfoType();
    }

    /**
     * Create an instance of {@link SignatureStandardOptionsType }
     * 
     */
    public SignatureStandardOptionsType createSignatureStandardOptionsType() {
        return new SignatureStandardOptionsType();
    }

    /**
     * Create an instance of {@link SignatureStatusRespType }
     * 
     */
    public SignatureStatusRespType createSignatureStatusRespType() {
        return new SignatureStatusRespType();
    }

    /**
     * Create an instance of {@link HashDocumentInfoType }
     * 
     */
    public HashDocumentInfoType createHashDocumentInfoType() {
        return new HashDocumentInfoType();
    }

    /**
     * Create an instance of {@link AttributeIdNameXmldsigOptionType }
     * 
     */
    public AttributeIdNameXmldsigOptionType createAttributeIdNameXmldsigOptionType() {
        return new AttributeIdNameXmldsigOptionType();
    }

    /**
     * Create an instance of {@link DocumentSignatureStatusInfoType }
     * 
     */
    public DocumentSignatureStatusInfoType createDocumentSignatureStatusInfoType() {
        return new DocumentSignatureStatusInfoType();
    }

    /**
     * Create an instance of {@link BatchInfoType }
     * 
     */
    public BatchInfoType createBatchInfoType() {
        return new BatchInfoType();
    }

    /**
     * Create an instance of {@link MultipleSignaturesXmldsigOptionType }
     * 
     */
    public MultipleSignaturesXmldsigOptionType createMultipleSignaturesXmldsigOptionType() {
        return new MultipleSignaturesXmldsigOptionType();
    }

    /**
     * Create an instance of {@link BatchSignatureReqType }
     * 
     */
    public BatchSignatureReqType createBatchSignatureReqType() {
        return new BatchSignatureReqType();
    }

    /**
     * Create an instance of {@link BandwidthInfoType }
     * 
     */
    public BandwidthInfoType createBandwidthInfoType() {
        return new BandwidthInfoType();
    }

    /**
     * Create an instance of {@link AddSubjectNameXmldsigOptionType }
     * 
     */
    public AddSubjectNameXmldsigOptionType createAddSubjectNameXmldsigOptionType() {
        return new AddSubjectNameXmldsigOptionType();
    }

    /**
     * Create an instance of {@link ValidatePdfReqType }
     * 
     */
    public ValidatePdfReqType createValidatePdfReqType() {
        return new ValidatePdfReqType();
    }

    /**
     * Create an instance of {@link SignatureStatusReqType }
     * 
     */
    public SignatureStatusReqType createSignatureStatusReqType() {
        return new SignatureStatusReqType();
    }

    /**
     * Create an instance of {@link SignatureRespType }
     * 
     */
    public SignatureRespType createSignatureRespType() {
        return new SignatureRespType();
    }

    /**
     * Create an instance of {@link ValidateReqType }
     * 
     */
    public ValidateReqType createValidateReqType() {
        return new ValidateReqType();
    }

    /**
     * Create an instance of {@link AddKeyValXmldsigOptionType }
     * 
     */
    public AddKeyValXmldsigOptionType createAddKeyValXmldsigOptionType() {
        return new AddKeyValXmldsigOptionType();
    }

    /**
     * Create an instance of {@link SignatureComplexDocumentReqType }
     * 
     */
    public SignatureComplexDocumentReqType createSignatureComplexDocumentReqType() {
        return new SignatureComplexDocumentReqType();
    }

    /**
     * Create an instance of {@link ValidateRespType }
     * 
     */
    public ValidateRespType createValidateRespType() {
        return new ValidateRespType();
    }

    /**
     * Create an instance of {@link MobileUserType }
     * 
     */
    public MobileUserType createMobileUserType() {
        return new MobileUserType();
    }

    /**
     * Create an instance of {@link FindSignatureReqType }
     * 
     */
    public FindSignatureReqType createFindSignatureReqType() {
        return new FindSignatureReqType();
    }

    /**
     * Create an instance of {@link BatchSignatureTIDsRespType }
     * 
     */
    public BatchSignatureTIDsRespType createBatchSignatureTIDsRespType() {
        return new BatchSignatureTIDsRespType();
    }

    /**
     * Create an instance of {@link BatchSignatureRespTypeV2 }
     * 
     */
    public BatchSignatureRespTypeV2 createBatchSignatureRespTypeV2() {
        return new BatchSignatureRespTypeV2();
    }

    /**
     * Create an instance of {@link TemplateType }
     * 
     */
    public TemplateType createTemplateType() {
        return new TemplateType();
    }

    /**
     * Create an instance of {@link BatchSignatureReqTypeV2 }
     * 
     */
    public BatchSignatureReqTypeV2 createBatchSignatureReqTypeV2() {
        return new BatchSignatureReqTypeV2();
    }

    /**
     * Create an instance of {@link ElementsNameXmldsigOptionType }
     * 
     */
    public ElementsNameXmldsigOptionType createElementsNameXmldsigOptionType() {
        return new ElementsNameXmldsigOptionType();
    }

    /**
     * Create an instance of {@link ElementsIdXmldsigOptionType }
     * 
     */
    public ElementsIdXmldsigOptionType createElementsIdXmldsigOptionType() {
        return new ElementsIdXmldsigOptionType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "DateType")
    public JAXBElement<DateType> createDateType(DateType value) {
        return new JAXBElement<DateType>(_DateType_QNAME, DateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlgorithmFilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "AlgorithmFilterType")
    public JAXBElement<AlgorithmFilterType> createAlgorithmFilterType(AlgorithmFilterType value) {
        return new JAXBElement<AlgorithmFilterType>(_AlgorithmFilterType_QNAME, AlgorithmFilterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OwnerFilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "OwnerFilterType")
    public JAXBElement<OwnerFilterType> createOwnerFilterType(OwnerFilterType value) {
        return new JAXBElement<OwnerFilterType>(_OwnerFilterType_QNAME, OwnerFilterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForceHardwareFilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "ForceHardwareFilterType")
    public JAXBElement<ForceHardwareFilterType> createForceHardwareFilterType(ForceHardwareFilterType value) {
        return new JAXBElement<ForceHardwareFilterType>(_ForceHardwareFilterType_QNAME, ForceHardwareFilterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateFiltersType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "certificateFiltersType")
    public JAXBElement<CertificateFiltersType> createCertificateFiltersType(CertificateFiltersType value) {
        return new JAXBElement<CertificateFiltersType>(_CertificateFiltersType_QNAME, CertificateFiltersType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "StringType")
    public JAXBElement<StringType> createStringType(StringType value) {
        return new JAXBElement<StringType>(_StringType_QNAME, StringType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "FaultDetail")
    public JAXBElement<StatusType> createFaultDetail(StatusType value) {
        return new JAXBElement<StatusType>(_FaultDetail_QNAME, StatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoubleType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "DoubleType")
    public JAXBElement<DoubleType> createDoubleType(DoubleType value) {
        return new JAXBElement<DoubleType>(_DoubleType_QNAME, DoubleType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HsmCertificateFilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "HsmCertificateFilterType")
    public JAXBElement<HsmCertificateFilterType> createHsmCertificateFilterType(HsmCertificateFilterType value) {
        return new JAXBElement<HsmCertificateFilterType>(_HsmCertificateFilterType_QNAME, HsmCertificateFilterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IntegerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "IntegerType")
    public JAXBElement<IntegerType> createIntegerType(IntegerType value) {
        return new JAXBElement<IntegerType>(_IntegerType_QNAME, IntegerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TrustChainFilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esec.com.br/mss/ap", name = "TrustChainFilterType")
    public JAXBElement<TrustChainFilterType> createTrustChainFilterType(TrustChainFilterType value) {
        return new JAXBElement<TrustChainFilterType>(_TrustChainFilterType_QNAME, TrustChainFilterType.class, null, value);
    }

}
