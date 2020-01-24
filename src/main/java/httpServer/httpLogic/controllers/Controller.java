package httpServer.httpLogic.controllers;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class Controller {

    protected final Router router;
    protected final Request request;

    public Controller(Router router, Request request) {
        this.router = router;
        this.request = request;
    }

    public Response options() {
        Set<String> recognizedMethods = this.getRecognizedMethods();
        String stringOfRecognizedMethods = stringifyRecognizedMethods(recognizedMethods);
        return new ResponseBuilder()
                .addHeader("Allow", stringOfRecognizedMethods)
                .finalizeMetaDataForOKResponse()
                .build();
    }

    public Set<String> getRecognizedMethods() {
        Method[] classMethods = this.getClass().getMethods();
        return parseMethodsThatReturnResponseObjects(classMethods);
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

    private String stringifyRecognizedMethods(Set<String> recognizedMethods) {
        StringBuilder stringBuilder = new StringBuilder();
        recognizedMethods.forEach((method) -> {
            stringBuilder.append(method.toUpperCase());
            stringBuilder.append(", ");
        });
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
        return stringBuilder.toString();
    }

    public Response head() {
        Response responseToReturn;
        try {
            Method methodToInvoke = this.getClass().getMethod(HTTPMethods.GET.toLowerCase());
            Response fullResponse = (Response) methodToInvoke.invoke(this);
            responseToReturn = new ResponseBuilder()
                    .setHeaders(fullResponse.getHeaders())
                    .finalizeMetaDataForOKResponse()
                    .build();
        } catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e) {
            responseToReturn = new ResponseBuilder()
                    .addStatusCode("400")
                    .build();
        }
        return responseToReturn;
    }

}
