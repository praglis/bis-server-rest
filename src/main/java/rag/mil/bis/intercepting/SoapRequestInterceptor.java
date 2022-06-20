//package rag.mil.bis.intercepting;
//
//import org.apache.cxf.binding.soap.SoapMessage;
//import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
//import org.apache.cxf.headers.Header;
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.phase.Phase;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import rag.mil.bis.exception.SecurityFault;
//
//import java.util.List;
//import java.util.Optional;
//
//
//public class SoapRequestInterceptor extends AbstractSoapInterceptor { //todo na filter
//    public static final String WSS_USERNAME_HEADER = "WS-Security-Username";
//    public static final String BIS_CLIENT_USERNAME = "BIS-client";
//    public static final String WSS_PASSWORD_HEADER = "WS-Security-Password";
//    public static final String BIS_CLIENT_PASSWORD = "Runner78910";
//    private static final Logger logger = LoggerFactory.getLogger(SoapRequestInterceptor.class);
//    private static final String UNKNOWN_USER_MSG = "Unknown user.";
//    private static final String WRONG_PASSWORD_MSG = "Wrong password.";
//
//
//    public SoapRequestInterceptor() {
//        super(Phase.PRE_PROTOCOL);
//    }
//
//    @Override
//    public void handleMessage(SoapMessage message) throws Fault {
//        logger.info("--- Started handling SOAP request ---");
//        List<Header> headers = message.getHeaders();
//        HeaderUtils.logHeaders("SOAP request headers: ", headers, logger);
//
//        checkSecurityHeaders(headers);
//
//        logger.info("--- Completed handling SOAP request ---");
//    }
//
//    private void checkSecurityHeaders(List<Header> headers) throws Fault {
//        Optional<Header> usernameHeader = headers.stream()
//                .filter(header -> header.getName().getLocalPart().equals(WSS_USERNAME_HEADER))
//                .findFirst();
//
//        Optional<Header> passwordHeader = headers.stream()
//                .filter(header -> header.getName().getLocalPart().equals(WSS_PASSWORD_HEADER))
//                .findFirst();
//
//        if (usernameHeader.isPresent() &&
//                new BisHeader(usernameHeader.get()).getValue().equals(BIS_CLIENT_USERNAME)) {
//            if (passwordHeader.isPresent() &&
//                    new BisHeader(passwordHeader.get()).getValue().equals(BIS_CLIENT_PASSWORD)) {
//                logger.info(String.format("Authenticated user: %s", usernameHeader.get().getObject()));
//            } else throw new SecurityFault(WRONG_PASSWORD_MSG);
//        } else throw new SecurityFault(UNKNOWN_USER_MSG);
//    }
//}
