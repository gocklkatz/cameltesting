package io.gocklkatz.cameltesting;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class CamelTesting3Test extends CamelTestSupport {

    @EndpointInject("mock:camel")
    private MockEndpoint mockCamel;

    @EndpointInject("mock:other")
    private MockEndpoint mockOther;

    @Produce("direct:start")
    private ProducerTemplate directStart;

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new SimpleTransformRouteBuilder();
    }

    @Test
    public void testPayloadIsTransformed() throws InterruptedException {
        mockCamel.expectedMessageCount(1);
        mockCamel.message(0).body().isEqualTo("Camel Rocks");
        mockCamel.message(0).header("verified").isEqualTo(true);
        directStart.sendBody("Camel Rocks");
        mockCamel.assertIsSatisfied();

        mockOther.expectedMessageCount(1);
        mockOther.message(0).body().isEqualTo("Spring Rocks");
        mockOther.message(0).header("verified").isNull();
        directStart.sendBody("Spring Rocks");
        mockOther.assertIsSatisfied();
    }

}
