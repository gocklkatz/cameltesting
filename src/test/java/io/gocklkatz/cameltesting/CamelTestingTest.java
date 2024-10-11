package io.gocklkatz.cameltesting;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;


class CamelTestingTest extends CamelTestSupport {

    @EndpointInject("mock:out")
    private MockEndpoint mockOut;

    @Produce("direct:in")
    private ProducerTemplate in;

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new SimpleTransformRouteBuilder();
    }

    @Test
    public void testPayloadIsTransformed() throws InterruptedException {
        //MockEndpoint mockOut = getMockEndpoint("mock:out");
        mockOut.setExpectedMessageCount(1);
        mockOut.message(0).body().isEqualTo("Modified: Cheese");
        //template.sendBody("direct:in", "Cheese");
        in.sendBody("Cheese");
        mockOut.assertIsSatisfied();
        //MockEndpoint.assertIsSatisfied(context);
    }
}
