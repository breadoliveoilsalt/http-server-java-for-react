package httpServer.httpLogic.controllers;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

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
        response.statusCode = HTTPStatusCodes.OK;
        response.addHeader(HTTPHeaders.Allow, getStringOfRecognizedMethods());
        return response;
    }

    public String getStringOfRecognizedMethods() {
        Set<String> recognizedMethods = getRecognizedMethods();
        return stringifyRecognizedMethods(recognizedMethods);
    }

    public Set<String> getRecognizedMethods() {
        Method[] classMethods = this.getClass().getMethods();
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
        try {
            subjectResponseToControllerGetMethod();
        } catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e) {
            response.statusCode = HTTPStatusCodes.MethodNotAllowed;
            response.addHeader(HTTPHeaders.Allow, this.getStringOfRecognizedMethods());
        }
        return response;
    }

    public void subjectResponseToControllerGetMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method methodToInvoke = this.getClass().getMethod(HTTPMethods.GET.toLowerCase());
        methodToInvoke.invoke(this);
    }

    public boolean respondsTo(String httpMethodRequested) {
       return getRecognizedMethods().contains(httpMethodRequested);
    }

}
