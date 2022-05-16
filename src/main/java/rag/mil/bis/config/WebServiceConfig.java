package rag.mil.bis.config;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import rag.mil.bis.events.EventController;
import rag.mil.bis.intercepting.SoapRequestInterceptor;
import rag.mil.bis.intercepting.SoapResponseInterceptor;
import rag.mil.bis.mtom.MtomController;


@EnableWs
@Configuration
@RequiredArgsConstructor
public class WebServiceConfig extends WsConfigurerAdapter {

    private final ApplicationContext applicationContext;

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        return new ServletRegistrationBean(servlet, "/soap/*");
    }

    @Bean
    public ServletRegistrationBean<CXFServlet> disServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/soap-api/*");
    }

    @Bean
    public EndpointImpl endpoint(EventController eventController) {
        Bus bus = applicationContext.getBean(SpringBus.class);
        EndpointImpl eventsEndpoint = new EndpointImpl(bus, eventController);

        eventsEndpoint.getInInterceptors().add(new SoapRequestInterceptor());
        eventsEndpoint.getOutInterceptors().add(new SoapResponseInterceptor());

        eventsEndpoint.publish("/events");
        return eventsEndpoint;
    }

    @Bean
    public EndpointImpl mtomEndpoint(MtomController mtomController) {
        Bus bus = applicationContext.getBean(SpringBus.class);
        EndpointImpl mtomEndpoint = new EndpointImpl(bus, mtomController);

        mtomEndpoint.getInInterceptors().add(new SoapRequestInterceptor());
        mtomEndpoint.getOutInterceptors().add(new SoapResponseInterceptor());

        mtomEndpoint.publish("/mtom");
        return mtomEndpoint;
    }
}
