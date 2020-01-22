package httpServer.httpLogic.router;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.responses.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class RouterBuilder {

    private final Map<String, Controller> routeMap;

    public RouterBuilder() {
        this.routeMap = new HashMap<>();
    }

//    private final ArrayList<PathAndMethodRoute> pathAndMethodRouteList;
//
//    public RouterBuilder() {
//        this.pathAndMethodRouteList = new ArrayList<>();
//    }
//
//    public PathAndMethodRoute createPath(String name) {
//        PathAndMethodRoute pathAndMethodRoute = new PathAndMethodRoute(name);
//        pathAndMethodRouteList.add(pathAndMethodRoute);
//        return pathAndMethodRoute;
//    }
//
//    public Router build() {
//        Map<String, Map<String, Callable<Response>>> tempMap = new HashMap<>();
//        pathAndMethodRouteList.forEach(pathAndMethodRoute -> {
//            tempMap.put(pathAndMethodRoute.getPathName(), pathAndMethodRoute.getMethodActionMap());
//        });
//        return new Router(tempMap);
//    }
}
