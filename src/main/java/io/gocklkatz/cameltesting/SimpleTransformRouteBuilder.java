package io.gocklkatz.cameltesting;

import org.apache.camel.builder.RouteBuilder;

public class SimpleTransformRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:in")
                .transform(simple("Modified: ${body}"))
                .to("mock:out");

        from("direct:start")
                .choice()
                    .when().simple("${body} contains 'Camel'")
                        .setHeader("verified").constant(true)
                        .to("mock:camel")
                    .otherwise()
                        .to("mock:other")
                    .end();
    }
}
