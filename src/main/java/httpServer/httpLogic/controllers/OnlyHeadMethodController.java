package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.router.Router;

public class OnlyHeadMethodController extends Controller {

    public OnlyHeadMethodController(Router router, Request request) {
        super(router, request);
    }
}
