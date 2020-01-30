package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class OnlyHeadMethodController extends Controller {

    public OnlyHeadMethodController(Request request, Response response) {
        super(request, response);
    }
}
