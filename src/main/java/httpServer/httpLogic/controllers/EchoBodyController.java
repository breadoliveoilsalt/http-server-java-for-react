package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class EchoBodyController {

    public static Response post(Request request) {
        System.out.println("Here's the body");
        System.out.println(request.getBody());
        return new ResponseBuilder()
                .addOKStatusLine()
                .addBody(request.getBody())
                .build();
    }
}
