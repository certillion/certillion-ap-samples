
package br.com.esec.mss.ap;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "FaultDetail", targetNamespace = "http://esec.com.br/mss/ap")
public class ICPMException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private StatusType faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ICPMException(String message, StatusType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ICPMException(String message, StatusType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: br.com.esec.mss.ap.StatusType
     */
    public StatusType getFaultInfo() {
        return faultInfo;
    }

}
