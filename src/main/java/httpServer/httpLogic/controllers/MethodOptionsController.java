package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class MethodOptionsController extends Controller {

    public MethodOptionsController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        return response;
    }

}
