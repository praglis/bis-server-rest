package rag.mil.bis.intercepting;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SoapRequestInterceptor extends AbstractSoapInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SoapRequestInterceptor.class);

    public SoapRequestInterceptor() {
        super(Phase.USER_LOGICAL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        logger.info("--- Started handling SOAP request ---");
        List<Header> headers = message.getHeaders();
        HeaderUtils.logHeaders("SOAP request headers: ", headers, logger);
        logger.info("--- Completed handling SOAP request ---");
    }
}
