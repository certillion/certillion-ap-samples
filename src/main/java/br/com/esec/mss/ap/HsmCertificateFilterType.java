package br.com.esec.mss.ap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HsmCertificateFilterType")
@XmlType(name = "HsmCertificateFilterType")
public class HsmCertificateFilterType {

	@XmlAttribute(name = "value", required = true)
	private boolean value;

	public HsmCertificateFilterType(boolean value) {
		this.value = value;
	}

	public HsmCertificateFilterType() {

	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	public Object getValue() {
		return value;
	}

}
