package httpServer.httpLogic.controllerLogic;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseFactory;

import java.util.*;
import java.util.concurrent.Callable;

public class Controller {

    private final Map<String, Map<String, Callable<Response>>> routeMap;
    private final HashSet<String> allowedMethods;

    public Controller(Map<String, Map<String, Callable<Response>>> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
        this.allowedMethods = new HashSet<>();
        populateAllowedMethods();
    }

    private void populateAllowedMethods() {
        routeMap.forEach( (path, methodAndAction) -> {
            Set<String> listofMethodsForPath = methodAndAction.keySet();
            allowedMethods.add("HEAD");
            allowedMethods.addAll(listofMethodsForPath);
        });
    }

    public Response handle(Request request) throws Exception {
        Response responseToReturn;
        if (request.isInvalid()) {
            responseToReturn = ResponseFactory.build400Response();
        } else if (hasUnrecognizedMethod(request)) {
            responseToReturn = ResponseFactory.build501Response();
        } else if (validHEADRequest(request)) {
            Callable<Response> action = getActionFor(request.getPath(), "GET");
            Response fullResponse = action.call();
            responseToReturn = ResponseFactory.buildHEADResponseFor(fullResponse);
        } else {
            Callable<Response> action = getActionFor(request.getPath(), request.getMethod());
            responseToReturn = action.call();
        }
        return responseToReturn;
    }

    private boolean hasUnrecognizedMethod(Request request) {
        return !allowedMethods.contains(request.getMethod());
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
