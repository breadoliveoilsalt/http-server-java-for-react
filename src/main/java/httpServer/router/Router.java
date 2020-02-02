package httpServer.router;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.responses.Response;

import java.lang.reflect.Method;
import java.util.*;

public class Router {

    private final Map<String, Class<Controller>> routeMap;
    private Set<String> recognizedHTTPMethods;

    public Router(Map<String, Class<Controller>> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
    }

    public Class<Controller> getControllerFor(String path) {
       return routeMap.get(path);
    }

    public Set<String> getAllRecognizedHTTPMethods() {
        if (recognizedHTTPMethods == null) {
            recognizedHTTPMethods = new HashSet<>();
            populateRecognizedMethods();
        }
        return recognizedHTTPMethods;
    }

    private void populateRecognizedMethods() {
        routeMap.values().forEach( controllerClass -> {
            Method[] classMethods = controllerClass.getMethods();
            Set<String> parsedMethods = parseMethodsThatReturnResponseObjects(classMethods);
            recognizedHTTPMethods.addAll(parsedMethods);
        });
    }

    private Set<String> parseMethodsThatReturnResponseObjects(Method[] classMethods) {
        HashSet<String> parsedMethods = new HashSet<>();
        for (Method method : classMethods) {
            if (method.getReturnType() == Response.class) {
                parsedMethods.add(method.getName().toUpperCase());
            }
        }
        return parsedMethods;
    }

    public Set<String> getPaths() {
        return routeMap.keySet();
    }

}
