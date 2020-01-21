package httpServer.httpLogic.controllers;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseFactory;

public class GetWithBodyController {

    public static Response get() {
        return ResponseFactory.buildSimpleResponse();
    }
}
