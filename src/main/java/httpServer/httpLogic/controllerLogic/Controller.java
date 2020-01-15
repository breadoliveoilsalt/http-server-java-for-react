package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class Controller {

    private Map<String, Map<String, Callable<Response>>> routeMap;
    private ArrayList<String> validPaths;

    public Controller(Map<String, Map<String, Callable<Response>>> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
    }

    public Response handle(Request request) throws Exception {
        String pathRequested = request.getPath();
        String methodRequested = request.getMethod();
        Callable<Response> action = get(pathRequested, methodRequested);
        return action.call();
    }

    private Callable<Response> get(String path, String method) {
        return routeMap.get(path).get(method);
    }

    public Set<String> getPaths() {
        return routeMap.keySet();
    }

    public Set<String> getMethodsFor(String path) {
        return routeMap.get(path).keySet();
    }

    public Callable<Response> getActionFor(String path, String method) {
        return routeMap.get(path).get(method);
    }


}
