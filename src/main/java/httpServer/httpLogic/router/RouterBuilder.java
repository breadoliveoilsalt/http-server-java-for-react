package httpServer.httpLogic.router;

import httpServer.httpLogic.handler.Handler;
import httpServer.httpLogic.responses.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class RouterBuilder {

    private ArrayList<PathMethodRoute> pathMethodRouteList;

    public RouterBuilder() {
        this.pathMethodRouteList = new ArrayList<>();
    }

    public PathMethodRoute createPath(String name) {
        PathMethodRoute pathMethodRoute = new PathMethodRoute(name);
        pathMethodRouteList.add(pathMethodRoute);
        return pathMethodRoute;
    }

    public Handler build() {
        Map<String, Map<String, Callable<Response>>> tempMap = new HashMap<>();
        pathMethodRouteList.forEach(pathMethodRoute -> {
            tempMap.put(pathMethodRoute.getPathName(), pathMethodRoute.getMethodActionMap());
        });
        return new Handler(tempMap);
    }
}
