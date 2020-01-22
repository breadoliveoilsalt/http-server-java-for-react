package httpServer.httpLogic.router;

import httpServer.httpLogic.controllers.Controller;

import java.util.HashMap;
import java.util.Map;

public class RouterBuilder {

    private final Map<String, Class> routeMap;

    public RouterBuilder() {
        this.routeMap = new HashMap<>();
    }

    public RouterBuilder addPathAndController(String path, Class controllerClass) {
        routeMap.put(path, controllerClass);
        return this;
    }

    public Router build() {
        return new Router(routeMap);
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
