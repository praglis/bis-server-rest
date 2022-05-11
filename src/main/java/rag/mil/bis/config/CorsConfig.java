package rag.mil.bis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebMvc
public class CorsConfig implements Filter, WebMvcConfigurer {
    private static final String ACCESS_CONTROL_RESPONSE_HEADERS = "Access-Control-Expose-Headers";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        logger.info(String.format("WebConfig; %s", request.getRequestURI()));
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT");
        response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ACCESS_CONTROL_RESPONSE_HEADERS + "Authorization, content-type," +
                "USERID" + "ROLE" +
                "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe");
        logger.info(String.format("Request Method: %s", request.getMethod()));

        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
            try {
                chain.doFilter(req, res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Pre-flight");
            response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
            response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader(ACCESS_CONTROL_RESPONSE_HEADERS, "Authorization");
            response.addHeader(ACCESS_CONTROL_RESPONSE_HEADERS, "responseType");
            response.addHeader(ACCESS_CONTROL_RESPONSE_HEADERS, "observe");
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
