package httpServer.httpLogic.controllers;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public abstract class Controller {

    protected final Request request;
    protected final Response response;

    public Controller(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Response options() {
        return new ResponseBuilder()
                .addHeader("Allow", getStringOfRecognizedMethods())
                .finalizeMetaDataForOKResponse()
                .build();
    }

    public Set<String> getRecognizedMethods() {
        Method[] classMethods = this.getClass().getMethods();
        return parseMethodsThatReturnResponseObjects(classMethods);
    }

    public String getStringOfRecognizedMethods() {
        Set<String> recognizedMethods = getRecognizedMethods();
        return stringifyRecognizedMethods(recognizedMethods);
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
            responseToReturn = new ExceptionsController().render405Response(this);
        }
        return responseToReturn;
    }

    public boolean respondsTo(String httpMethodRequested) {
       return getRecognizedMethods().contains(httpMethodRequested);
    }

}
