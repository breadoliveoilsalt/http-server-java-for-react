package httpServer.httpLogic.routes;

import httpServer.httpLogic.Response;
import httpServer.httpLogic.ResponseFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class RouteMap {

    Map<String, Map<String, Callable>> routeMap;

    public RouteMap(Map<String, Map<String, Callable>> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
    }

    public Callable get(String path, String method) {
        Callable action;
        ArrayList<Object> methodAndActionList = routeMap.get(path);
        methodAndActionList.forEach(list -> {
//            if (list[0] == method) {
//                action = list[1];
//            }
        });
        return action;

    }
}
