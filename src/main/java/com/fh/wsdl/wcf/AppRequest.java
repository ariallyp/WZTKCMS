
package com.fh.wsdl.wcf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppRequest", namespace = "http://data.easc.net/entities", propOrder = {
    "appId",
    "data",
    "methodName",
    "userHostAddress",
    "userHostName"
})
public class AppRequest {

    @XmlElementRef(name = "AppId", namespace = "http://data.easc.net/entities", type = JAXBElement.class, required = false)
    protected JAXBElement<String> appId;
    @XmlElementRef(name = "Data", namespace = "http://data.easc.net/entities", type = JAXBElement.class, required = false)
    protected JAXBElement<Object> data;
    @XmlElementRef(name = "MethodName", namespace = "http://data.easc.net/entities", type = JAXBElement.class, required = false)
    protected JAXBElement<String> methodName;
    @XmlElementRef(name = "UserHostAddress", namespace = "http://data.easc.net/entities", type = JAXBElement.class, required = false)
    protected JAXBElement<String> userHostAddress;
    @XmlElementRef(name = "UserHostName", namespace = "http://data.easc.net/entities", type = JAXBElement.class, required = false)
    protected JAXBElement<String> userHostName;

    /**
     * ��ȡappId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAppId() {
        return appId;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAppId(JAXBElement<String> value) {
        this.appId = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     
     */
    public JAXBElement<Object> getData() {
        return data;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     
     */
    public void setData(JAXBElement<Object> value) {
        this.data = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMethodName() {
        return methodName;
    }

    /**
     *
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMethodName(JAXBElement<String> value) {
        this.methodName = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUserHostAddress() {
        return userHostAddress;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserHostAddress(JAXBElement<String> value) {
        this.userHostAddress = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUserHostName() {
        return userHostName;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserHostName(JAXBElement<String> value) {
        this.userHostName = value;
    }

}
