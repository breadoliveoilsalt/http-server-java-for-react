package httpServer.httpLogic.router;

import httpServer.httpLogic.responses.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ControllerBuilder {

    private ArrayList<PathMethodMap> pathMethodMapList;

    public ControllerBuilder() {
        this.pathMethodMapList = new ArrayList<>();
    }

    public PathMethodMap createPath(String name) {
        PathMethodMap pathMethodMap = new PathMethodMap(name);
        pathMethodMapList.add(pathMethodMap);
        return pathMethodMap;
    }

    public Controller build() {
        Map<String, Map<String, Callable<Response>>> tempMap = new HashMap<>();
        pathMethodMapList.forEach(pathMethodMap -> {
            tempMap.put(pathMethodMap.getPathName(), pathMethodMap.getMethodActionMap());
        });
        return new Controller(tempMap);
    }
}
