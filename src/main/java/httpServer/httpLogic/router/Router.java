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

    public Set<String> getRecognizedMethods() {
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

    public Set<String> getPaths() {
        return routeMap.keySet();
    }

    public Set<String> getMethodsFor(String path) {
        Class controllerClass = routeMap.get(path);
        Method[] classMethods = controllerClass.getMethods();
        return parseMethodsThatReturnResponseObjects(classMethods);
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

}
