package httpServer.httpLogic.router;

import httpServer.httpLogic.responses.Response;

import java.lang.reflect.Method;
import java.util.*;

public class Router {

    private final Map<String, Class> routeMap;
    private Set<String> recognizedMethods;

    public Router(Map<String, Class> routeMap) {
        this.routeMap = Collections.unmodifiableMap(routeMap);
    }

    public Class getControllerFor(String path) {
       return routeMap.get(path);
    }

    public Set<String> getAllRecognizedMethods() {
        if (recognizedMethods == null) {
            recognizedMethods = new HashSet<>();
            populateRecognizedMethods();
        }
        return recognizedMethods;
    }

    private void populateRecognizedMethods() {
        routeMap.values().forEach( controllerClass -> {
            Method[] classMethods = controllerClass.getMethods();
            Set<String> parsedMethods = parseMethodsThatReturnResponseObjects(classMethods);
            recognizedMethods.addAll(parsedMethods);
        });
    }

    private Set<String> parseMethodsThatReturnResponseObjects(Method[] classMethods) {
        HashSet<String> parsedMethods = new HashSet<>();
        for (Method method : classMethods) {
            if (method.getReturnType() == Response.class) {
                parsedMethods.add(method.getName());
            }
        }
        return parsedMethods;
    }

    public Set<String> getPaths() {
        return routeMap.keySet();
    }

}
