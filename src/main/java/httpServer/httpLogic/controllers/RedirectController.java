package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class RedirectController extends Controller {

    public RedirectController(Request request, Response response) {
       super(request, response);
    }

    public Response get() {
        return new ExceptionsController().render301ResponseRedirectingTo("/simple_get");
    }
}
