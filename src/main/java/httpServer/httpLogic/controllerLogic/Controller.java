package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.Request;
import httpServer.httpLogic.Response;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

public class Controller {

    Map<String, Map<String, Callable<Response>>> routeMap;

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

}
