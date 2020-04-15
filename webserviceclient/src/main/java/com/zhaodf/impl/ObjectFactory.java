
package com.zhaodf.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zhaodf.impl package. 
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

    private final static QName _Speak_QNAME = new QName("http://impl.zhaodf.com/", "speak");
    private final static QName _SpeakResponse_QNAME = new QName("http://impl.zhaodf.com/", "speakResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zhaodf.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Speak }
     * 
     */
    public Speak createSpeak() {
        return new Speak();
    }

    /**
     * Create an instance of {@link SpeakResponse }
     * 
     */
    public SpeakResponse createSpeakResponse() {
        return new SpeakResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Speak }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.zhaodf.com/", name = "speak")
    public JAXBElement<Speak> createSpeak(Speak value) {
        return new JAXBElement<Speak>(_Speak_QNAME, Speak.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SpeakResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.zhaodf.com/", name = "speakResponse")
    public JAXBElement<SpeakResponse> createSpeakResponse(SpeakResponse value) {
        return new JAXBElement<SpeakResponse>(_SpeakResponse_QNAME, SpeakResponse.class, null, value);
    }

}
