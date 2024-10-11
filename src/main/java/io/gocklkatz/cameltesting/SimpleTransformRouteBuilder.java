package io.gocklkatz.cameltesting;

import org.apache.camel.builder.RouteBuilder;

public class SimpleTransformRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:in")
                .transform(simple("Modified: ${body}"))
                .to("mock:out");
    }
}
