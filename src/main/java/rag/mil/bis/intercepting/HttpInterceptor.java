package rag.mil.bis.intercepting;

import org.apache.cxf.interceptor.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rag.mil.bis.exceptions.security.BadSecurityHeaderException;
import rag.mil.bis.exceptions.security.MissingSecurityHeaderException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Configuration
@EnableWebMvc
public class HttpInterceptor implements Filter, WebMvcConfigurer {
    public static final String USERNAME_HEADER = "WS-Security-Username";
    public static final String BIS_CLIENT_USERNAME = "BIS-client";
    public static final String PASSWORD_HEADER = "WS-Security-Password";
    public static final String BIS_CLIENT_PASSWORD = "Runner78910";

    private static final String ACCESS_CONTROL_RESPONSE_HEADERS = "Access-Control-Expose-Headers";
    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

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

        checkSecurityHeaders(((HttpServletRequest) req));

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

    private void checkSecurityHeaders(HttpServletRequest req) throws Fault {
        String usernameHeader = extractHeader(req, USERNAME_HEADER);
        String passwordHeader = extractHeader(req, PASSWORD_HEADER);

        validateSecurityHeader(USERNAME_HEADER, usernameHeader, BIS_CLIENT_USERNAME);
        validateSecurityHeader(PASSWORD_HEADER, passwordHeader, BIS_CLIENT_PASSWORD);
        logger.info(String.format("Authenticated user: %s", usernameHeader));
    }

    private void validateSecurityHeader(String headerName, String actualHeaderVal, String expectedHeaderVal) {
        if (!actualHeaderVal.equals(expectedHeaderVal)) throw new BadSecurityHeaderException(headerName);
    }

    private String extractHeader(HttpServletRequest req, String headerName) {
        return Optional.ofNullable(req.getHeader(headerName))
                .orElseThrow(() -> new MissingSecurityHeaderException(headerName));
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_RESPONSE_HEADERS + "Authorization, content-type," +
                "USERID" + "ROLE" +
                "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe");
    }

    private void setResponseHeadersForOptionsMethod(HttpServletResponse response) {
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
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
