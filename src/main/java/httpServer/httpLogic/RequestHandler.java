package httpServer.httpLogic;

import httpServer.httpLogic.routes.RouteMap;

public class RequestHandler {

    // consider adding router as parameter!

    public Response handle(Request request, RouteMap routes) throws Exception {

        return RouteMap.routes.get(request.getPath()).get(request.getMethod()).call();
    }
}
