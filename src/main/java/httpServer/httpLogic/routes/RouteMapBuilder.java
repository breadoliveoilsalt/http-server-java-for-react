package httpServer.httpLogic.routes;

import java.util.ArrayList;
import java.util.HashMap;

public class RouteMapBuilder {

    private ArrayList<PathMethodMap> pathMethodMapList;

    public RouteMapBuilder() {
        this.pathMethodMapList = new ArrayList<>();
    }

    public PathMethodMap createPath(String name) {
        PathMethodMap pathMethodMap = new PathMethodMap(name);
        pathMethodMapList.add(pathMethodMap);
        return pathMethodMap;
    }

    public RouteMap buildRouteMap() {
        HashMap<String, Object> tempMap = new HashMap<>();
        pathMethodMapList.forEach(pathMethodMap -> {
//            tempMap.put(pathMethodMap.getPathName(), pathMethodMap.getMethodAndActionList());
            tempMap.put(pathMethodMap.getPathName(), )
        });
        return new RouteMap(tempMap);
    }
}
