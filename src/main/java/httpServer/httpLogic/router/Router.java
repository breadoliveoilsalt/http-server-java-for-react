package httpServer.httpLogic.router;

import httpServer.httpLogic.responses.Response;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

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
            addClassMethodsToRecognizedMethods(classMethods);
        });

//        routeMap.forEach((path, controllerClass) -> {
//            Method[] classMethods = controllerClass.getMethods();
//            addClassMethodsToRecognizedMethods(classMethods);
//        });
    }

    private void addClassMethodsToRecognizedMethods(Method[] classMethods) {
        for (Method method : classMethods) {
            if (method.getReturnType() == Response.class) {
                recognizedMethods.add(method.getName().toUpperCase());
            }
        }
    }
//    private final Map<String, Map<String, Callable<Response>>> routeMap;
//    private HashSet<String> recognizedMethods;
//
//    public Router(Map<String, Map<String, Callable<Response>>> routeMap) {
//        this.routeMap = Collections.unmodifiableMap(routeMap);
//    }
//
//    public HashSet<String> getRecognizedMethods() {
//        if (recognizedMethods == null) {
//            recognizedMethods = new HashSet<>();
//            populateAllowedMethods();
//        }
//        return recognizedMethods;
//    }
//    private void populateAllowedMethods() {
//        routeMap.forEach( (path, methodAndAction) -> {
//            Set<String> listOfMethodsForPath = methodAndAction.keySet();
//            recognizedMethods.addAll(listOfMethodsForPath);
//        });
//    }
//
//    public Set<String> getPaths() {
//        return routeMap.keySet();
//    }
//
//    public Set<String> getMethodsFor(String path) {
//        return routeMap.get(path).keySet();
//    }
//
//    public Callable<Response> getActionFor(String path, String method) {
//        return routeMap.get(path).get(method);
//    }
}
