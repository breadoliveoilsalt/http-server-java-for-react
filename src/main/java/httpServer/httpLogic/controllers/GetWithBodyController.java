package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class GetWithBodyController extends Controller {

    public GetWithBodyController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        response.stringBody = "Hello World!";
        return response;
    }

}
