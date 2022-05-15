package rag.mil.bis.intercepting;

import org.apache.cxf.headers.Header;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public interface HeaderUtils {
    static void logHeaders(String msg, List<Header> headers, Logger logger) {
        logger.info(msg);
        //noinspection RedundantCast
        headers.forEach(header -> logger.info(new BisHeader(header).toString()));
    }

    static void logHeaders(String msg, Enumeration<String> headers, Logger logger) {
        logger.info(msg);
        if (headers.hasMoreElements()) {
            logger.info(String.format("[header] %s", headers.nextElement()));
        }
    }

    static void logHeaders(String msg, Collection<String> headers, Logger logger) {
        logger.info(msg);
        List<String> headersList = (ArrayList<String>) headers;
        headersList.forEach(h -> logger.info(String.format("[header] %s", h)));
    }
}
