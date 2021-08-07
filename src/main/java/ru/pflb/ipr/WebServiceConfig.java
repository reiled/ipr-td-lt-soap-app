package ru.pflb.ipr;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/soap/ws/*");
    }

    @Bean(name = "ipr")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema iprSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("IprPort");
        wsdl11Definition.setLocationUri("/soap/ws");
        wsdl11Definition.setTargetNamespace("http://ipr.pflb.ru/soap");
        wsdl11Definition.setSchema(iprSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema iprSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ipr.xsd"));
    }
}