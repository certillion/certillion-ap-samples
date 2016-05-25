
package br.com.esec.mss.ap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SignatureService", targetNamespace = "http://esec.com.br/mss/ap", wsdlLocation = "http://192.168.2.22:8280/mss/serviceAp_dev.wsdl")
public class SignatureService
    extends Service
{

    private final static URL SIGNATURESERVICE_WSDL_LOCATION;
    private final static WebServiceException SIGNATURESERVICE_EXCEPTION;
    private final static QName SIGNATURESERVICE_QNAME = new QName("http://esec.com.br/mss/ap", "SignatureService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.2.22:8280/mss/serviceAp_dev.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SIGNATURESERVICE_WSDL_LOCATION = url;
        SIGNATURESERVICE_EXCEPTION = e;
    }

    public SignatureService() {
        super(__getWsdlLocation(), SIGNATURESERVICE_QNAME);
    }

    public SignatureService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SIGNATURESERVICE_QNAME, features);
    }

    public SignatureService(URL wsdlLocation) {
        super(wsdlLocation, SIGNATURESERVICE_QNAME);
    }

    public SignatureService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SIGNATURESERVICE_QNAME, features);
    }

    public SignatureService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SignatureService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SignaturePortType
     */
    @WebEndpoint(name = "SignaturePortType")
    public SignaturePortType getSignaturePortType() {
        return super.getPort(new QName("http://esec.com.br/mss/ap", "SignaturePortType"), SignaturePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SignaturePortType
     */
    @WebEndpoint(name = "SignaturePortType")
    public SignaturePortType getSignaturePortType(WebServiceFeature... features) {
        return super.getPort(new QName("http://esec.com.br/mss/ap", "SignaturePortType"), SignaturePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SIGNATURESERVICE_EXCEPTION!= null) {
            throw SIGNATURESERVICE_EXCEPTION;
        }
        return SIGNATURESERVICE_WSDL_LOCATION;
    }

}
