package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.ResponseFactory;

public class ControllerFactory {

    public Controller buildHTTPServerController() {
        ControllerBuilder builder = new ControllerBuilder();

        PathMethodMap simpleGetPath = builder.createPath("/simple_get");
        simpleGetPath.addMethodAndAction("GET", () -> ResponseFactory.buildSimpleResponse());

        return builder.buildRouteMap();
    }

}
