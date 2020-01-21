package httpServer.httpLogic.router;

import httpServer.httpLogic.responses.Response;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class Router {

    private final Map<String, Map<String, Callable<Response>>> routeMap;
    private HashSet<String> recognizedMethods;

    public Router(Map<String, Map<String, Callable<Response>>> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
    }

    public HashSet<String> getRecognizedMethods() {
        if (recognizedMethods == null) {
            recognizedMethods = new HashSet<>();
            populateAllowedMethods();
        }
        return recognizedMethods;
    }
    private void populateAllowedMethods() {
        routeMap.forEach( (path, methodAndAction) -> {
            Set<String> listOfMethodsForPath = methodAndAction.keySet();
            recognizedMethods.addAll(listOfMethodsForPath);
        });
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
