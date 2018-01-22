package com.britishgas.configuration;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kundan Sharma
 */
@Configuration
public class PortConfiguration {
    @Value("${server.secondaryPorts}")
    private String secondaryPorts;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory embeddedTomcat = new TomcatEmbeddedServletContainerFactory();
        Connector[] additionalConnectors = this.additionalConnector();
        if (additionalConnectors != null && additionalConnectors.length > 0) {
            embeddedTomcat.addAdditionalTomcatConnectors(additionalConnectors);
        }
        return embeddedTomcat;
    }

    private Connector[] additionalConnector() {
    	if(StringUtils.isEmpty(secondaryPorts))
    		return null;
        String[] ports = this.secondaryPorts.split(",");
        List<Connector> result = new ArrayList<>();
        for (String port : ports) {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(Integer.valueOf(port));
            result.add(connector);
        }
        return result.toArray(new Connector[] {});
    }
}