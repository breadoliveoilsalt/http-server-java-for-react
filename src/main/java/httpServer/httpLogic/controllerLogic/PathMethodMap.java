package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.responses.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class PathMethodMap {

    private final String pathName;
    private final Map<String, Callable<Response>> methodActionMap;

    public PathMethodMap(String pathName) {
        this.pathName = pathName;
        this.methodActionMap = new HashMap<>();
    }

    public PathMethodMap addMethodAndAction(String method, Callable<Response> action) {
        methodActionMap.put(method, action);
        return this;
    }

    public String getPathName() {
        return pathName;
    }

    public Map<String, Callable<Response>> getMethodActionMap() {
        return methodActionMap;
    }

}
