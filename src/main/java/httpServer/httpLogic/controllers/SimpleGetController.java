package httpServer.httpLogic.controllers;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class SimpleGetController {

    public static Response get() {
        return new ResponseBuilder()
                .addOKStatusLine()
                .build();
    }

}
