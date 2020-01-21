package httpServer.httpLogic.router;

import httpServer.httpLogic.responses.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class PathAndMethodRoute {

    private final String pathName;
    private final Map<String, Callable<Response>> methodToControllerMap;

    public PathAndMethodRoute(String pathName) {
        this.pathName = pathName;
        this.methodToControllerMap = new HashMap<>();
    }

    public PathAndMethodRoute addMethodAndAction(String method, Callable<Response> action) {
        methodToControllerMap.put(method, action);
        return this;
    }

    public String getPathName() {
        return pathName;
    }

    public Map<String, Callable<Response>> getMethodActionMap() {
        return methodToControllerMap;
    }

}
