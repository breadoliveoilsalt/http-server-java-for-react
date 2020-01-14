package httpServer.httpLogic.routes;

import java.util.Map;
import java.util.concurrent.Callable;

public class PathMethodMap {

    private String pathName;
    private Map<String, Callable> methodActionMap;

    public PathMethodMap(String pathName) {
        this.pathName = pathName;
    }

    public PathMethodMap addMethodAndAction(String method, Callable action) {
        methodActionMap.put(method, action);
        return this;
    }

    public String getPathName() {
        return pathName;
    }

    public Map<String, Callable> getMethodActionMap() {
        return methodActionMap;
    }

}
