package httpServer.httpLogic.routes;

import httpServer.httpLogic.ResponseFactory;

public class RouteMapFactory {

    public RouteMap buildHTTPServerRoutes() {
        RouteMapBuilder builder = new RouteMapBuilder();

        PathMethodMap simpleGetPath = builder.createPath("/simple_get");
        simpleGetPath.addMethodAndAction("GET", () -> ResponseFactory.buildSimpleResponse());

        return builder.buildRouteMap();
    }

}
