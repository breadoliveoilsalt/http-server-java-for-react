package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class Controller {

    protected Router router;
    protected Request request;
    private Set<String> recognizedMethods;

    public Controller(Router router, Request request) {
        this.router = router;
        this.request = request;
    }

    public Response options() {
        getRecognizedMethods();
        String listOfRecognizedMethods = stringifyRecognizedMethods();
        return new ResponseBuilder()
                .addHeader("Accept", listOfRecognizedMethods)
                .finalizeMetaDataForOKResponse()
                .build();
    }

    public Set<String> getRecognizedMethods() {
        if (recognizedMethods == null) {
            recognizedMethods = new HashSet<>();
            populateRecognizedMethods();
        }
        return recognizedMethods;
    }

    private void populateRecognizedMethods() {
        Method[] classMethods = this.getClass().getMethods();
        Set<String> parsedMethods = parseMethodsThatReturnResponseObjects(classMethods);
        recognizedMethods.addAll(parsedMethods);
    }

    private String stringifyRecognizedMethods() {
        StringBuilder stringBuilder = new StringBuilder();
        recognizedMethods.forEach((method) -> {
            stringBuilder.append(method);
            stringBuilder.append(", ");
        });
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        return stringBuilder.toString();
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
