package io.gocklkatz.cameltesting;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CamelTesting2Test {

    private CamelContext camelContext;

    @BeforeEach
    public void setUpContext() throws Exception {
        this.camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new SimpleTransformRouteBuilder());
        camelContext.start();
    }

    @AfterEach
    public void cleanUpContext() throws Exception {
        camelContext.stop();
    }

    @Test
    public void testPayloadIsTransformed() throws InterruptedException {
        MockEndpoint mockOut = camelContext.getEndpoint("mock:out", MockEndpoint.class);
        ProducerTemplate template = camelContext.createProducerTemplate();

        mockOut.setExpectedMessageCount(1);
        mockOut.message(0).body().isEqualTo("Modified: Cheese");
        template.sendBody("direct:in", "Cheese");
        mockOut.assertIsSatisfied();
    }
}
