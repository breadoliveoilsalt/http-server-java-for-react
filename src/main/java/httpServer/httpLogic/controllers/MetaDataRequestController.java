package httpServer.httpLogic.controllers;

import httpServer.httpLogic.constants.Methods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

import java.util.HashSet;
import java.util.Set;

public class MetaDataRequestController {

//    public static Response buildHEADResponse(Router router, Request request) throws Exception {
//        Response fullResponse = router.getActionFor(request.getPath(), Methods.GET).call();
//
//        return new ResponseBuilder()
//                .addOKStatusLine()
//                .setHeaders(fullResponse.getHeaders())
//                .build();
//    }
//
//    public static Response buildOPTIONSResponse(Router router, Request request) {
//        Set<String> methods = extractMethods(router, request);
//        String stringOfMethods = stringifyMethods(methods);
//
//        return new ResponseBuilder()
//                .addOKStatusLine()
//                .addHeader("Allow", stringOfMethods)
//                .build();
//    }
//
//    private static Set<String> extractMethods(Router router, Request request) {
//        Set<String> methods = new HashSet<>(router.getMethodsFor(request.getPath()));
//        methods.add(Methods.OPTIONS);
//        if (methods.contains(Methods.GET)) {
//            methods.add(Methods.HEAD);
//        }
//        return methods;
//    }
//
//    private static String stringifyMethods(Set<String> methods) {
//        StringBuilder stringBuilder = new StringBuilder();
//        methods.forEach((method) -> {
//            stringBuilder.append(method);
//            stringBuilder.append(", ");
//        });
//        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());
//        return stringBuilder.toString();
//    }

}

