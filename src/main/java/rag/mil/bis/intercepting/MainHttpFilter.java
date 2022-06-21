package rag.mil.bis.intercepting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static rag.mil.bis.intercepting.SecurityFilter.PASSWORD_HEADER;
import static rag.mil.bis.intercepting.SecurityFilter.USERNAME_HEADER;

@Configuration
@Order(0)
public class MainHttpFilter implements Filter, WebMvcConfigurer {
    private static final String ACCESS_CONTROL_RESPONSE_HEADERS = "Access-Control-Expose-Headers";
    private static final Logger logger = LoggerFactory.getLogger(MainHttpFilter.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        logger.info("--- Started handling HTTP operation ---");

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        logHeaders("HTTP request headers: ", request, "HTTP response headers: ", response, String.format("HTTP request: %s", request));

        logger.info(String.format("Endpoint: %s", request.getRequestURI()));
        setResponseHeaders(response);
        logger.info(String.format("Request Method: %s", request.getMethod()));

        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
            try {
                chain.doFilter(req, res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Pre-flight");
            setResponseHeadersForOptionsMethod(response);
        }

        logHeaders("HTTP request headers after handling: ", request, "HTTP response headers after handling: ", response, "--- Completed handling HTTP operation ---");
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void setResponseHeadersForOptionsMethod(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8080");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, observe, " + USERNAME_HEADER + ", " + PASSWORD_HEADER);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader(ACCESS_CONTROL_RESPONSE_HEADERS, "Authorization");
        response.addHeader(ACCESS_CONTROL_RESPONSE_HEADERS, "responseType");
        response.addHeader(ACCESS_CONTROL_RESPONSE_HEADERS, "observe");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void logHeaders(String msg, HttpServletRequest request, String msg1, HttpServletResponse response, String request1) {
        HeaderUtils.logHeaders(msg, request.getHeaderNames(), logger);
        HeaderUtils.logHeaders(msg1, response.getHeaderNames(), logger);
        logger.info(request1);
    }
}
