package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class Controller {

    private final Map<String, Map<String, Callable<Response>>> routeMap;

    public Controller(Map<String, Map<String, Callable<Response>>> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
    }

    public Response handle(Request request) throws Exception {
        Response responseToReturn;
        if (validHEADRequest(request)) {
            Callable<Response> action = getActionFor(request.getPath(), "GET");
            Response fullResponse = action.call();
            responseToReturn = ResponseFactory.buildHEADResponseFor(fullResponse);
        } else {
            Callable<Response> action = getActionFor(request.getPath(), request.getMethod());
            responseToReturn = action.call();
        }
        return responseToReturn;
    }

    private boolean validHEADRequest(Request request) {
        return request.getMethod().equals("HEAD") && getMethodsFor(request.getPath()).contains("GET");
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
