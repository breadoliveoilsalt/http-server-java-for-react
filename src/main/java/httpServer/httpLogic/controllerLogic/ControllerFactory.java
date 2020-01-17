package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.responses.ResponseFactory;

public class ControllerFactory {

    public Controller buildHTTPServerController() {
        ControllerBuilder builder = new ControllerBuilder();

        builder
            .createPath("/simple_get")
            .addMethodAndAction("GET", () -> ResponseFactory.buildSimpleResponse());

        return builder.build();
    }

}
