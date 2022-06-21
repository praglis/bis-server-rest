package rag.mil.bis.intercepting;

import org.apache.cxf.interceptor.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rag.mil.bis.exceptions.security.BadSecurityHeaderException;
import rag.mil.bis.exceptions.security.MissingSecurityHeaderException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Configuration
@Order(2)
public class SecurityFilter implements Filter, WebMvcConfigurer {
    public static final String USERNAME_HEADER = "WS-Security-Username";
    public static final String BIS_CLIENT_USERNAME = "BIS-client";
    public static final String PASSWORD_HEADER = "WS-Security-Password";
    public static final String BIS_CLIENT_PASSWORD = "Runner78910";

    private static final String ACCESS_CONTROL_RESPONSE_HEADERS = "Access-Control-Expose-Headers";
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    private static final CharSequence SWAGGER_URI = "swagger";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        if (!((HttpServletRequest) req).getRequestURI().contains(SWAGGER_URI))
            checkSecurityHeaders(((HttpServletRequest) req));
        chain.doFilter(req, res);
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
}
