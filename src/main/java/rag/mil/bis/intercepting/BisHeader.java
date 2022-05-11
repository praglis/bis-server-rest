package rag.mil.bis.intercepting;

import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

public class BisHeader extends Header {
    public BisHeader(String headerName, Object headerValue) throws JAXBException {
        super(new QName(headerName), headerValue, new JAXBDataBinding(String.class));
    }

    @Override
    public String toString() {
        return "BisHeader -> " + this.getName() + ": " + this.getObject();
    }
}
