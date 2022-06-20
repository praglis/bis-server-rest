//package rag.mil.bis.intercepting;
//
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.phase.Phase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;//todo
//
//public class SoapResponseInterceptor extends AbstractSoapInterceptor {
//    private static final Logger logger = LoggerFactory.getLogger(SoapResponseInterceptor.class);
//
//    public SoapResponseInterceptor() {
//        super(Phase.USER_LOGICAL);
//    }
//
//    @Override
//    public void handleMessage(SoapMessage message) throws Fault {
//        logger.info("--- Started handling SOAP response ---");
//
//        try {
////            request headers
////            Map<String, List<String>> map = (Map<String, List<String>>) message.get(MessageContext.HTTP_REQUEST_HEADERS);
//
//            //this is underlying http response object
////                HttpServletResponse response = (HttpServletResponse) message.get(MessageContext.SERVLET_RESPONSE);
//
//            List<Header> headers = message.getHeaders();
//            if (headers != null) {
//                HeaderUtils.logHeaders("SOAP response headers before handling: ", headers, logger);
//                headers.add(new BisHeader("Header-From-Interceptor-Name", "header from interceptor value"));
//                HeaderUtils.logHeaders("SOAP response headers after handling: ", headers, logger);
//            } else logger.warn("No headers object");
//
//            logger.info("--- Completed handling SOAP response ---");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
