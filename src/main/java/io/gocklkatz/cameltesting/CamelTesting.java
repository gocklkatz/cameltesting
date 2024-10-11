package io.gocklkatz.cameltesting;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTesting {

    public static void main(String[] args) {
        try(CamelContext context = new DefaultCamelContext()) {
            context.addRoutes(new SimpleTransformRouteBuilder());

            context.start();
            Thread.sleep(10_000);
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
