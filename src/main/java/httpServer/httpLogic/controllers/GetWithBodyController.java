package httpServer.httpLogic.controllers;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class GetWithBodyController {

    public static Response get() {
        return new ResponseBuilder()
            .addOKStatusLine()
            .addBody("Hello World!")
            .build();
    }
}
