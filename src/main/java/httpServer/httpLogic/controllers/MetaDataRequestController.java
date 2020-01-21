package httpServer.httpLogic.controllers;

import httpServer.httpLogic.constants.Methods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

import java.util.HashSet;
import java.util.Set;

public class MetaDataRequestController {

    public static Response buildHEADResponse(Router router, Request request) throws Exception {
        Response fullResponse = router.getActionFor(request.getPath(), "GET").call();

        return new ResponseBuilder()
                .addOKStatusLine()
                .setHeaders(fullResponse.getHeaders())
                .build();
    }

    public static Response buildOPTIONSResponse(Router router, Request request) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> methods = new HashSet<>(router.getMethodsFor(request.getPath()));
        methods.add(Methods.HEAD);
        methods.add(Methods.OPTIONS);

        methods.forEach((method) -> {
            stringBuilder.append(method);
            stringBuilder.append(", ");
        });
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());

        String stringListOfMethods = stringBuilder.toString();

        return new ResponseBuilder()
                .addOKStatusLine()
                .addHeader("Allow", stringListOfMethods)
                .build();
    }

}

