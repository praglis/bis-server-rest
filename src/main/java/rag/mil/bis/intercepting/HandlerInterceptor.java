package rag.mil.bis.intercepting;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HandlerInterceptor extends AbstractSoapInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    public HandlerInterceptor() {
        super(Phase.USER_LOGICAL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        logger.info("Handling soap message by INTERCEPTOR...");
        try {
//            request headers
//            Map<String, List<String>> map = (Map<String, List<String>>) message.get(MessageContext.HTTP_REQUEST_HEADERS);

            //this is underlying http response object
//                HttpServletResponse response = (HttpServletResponse) message.get(MessageContext.SERVLET_RESPONSE);

            List<Header> headers = message.getHeaders();
            if (headers != null) {
                logHeaders("Intercepted headers: ", headers);
                headers.add(new BisHeader("Header-From-Interceptor-Name", "header from interceptor value"));
                logHeaders("Headers after handling: ", headers);
            } else logger.warn("No headers object");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logHeaders(String msg, List<Header> headers) {
        logger.info(msg);
        //noinspection RedundantCast
        headers.forEach(header -> logger.info(((BisHeader) header).toString()));
    }
}
