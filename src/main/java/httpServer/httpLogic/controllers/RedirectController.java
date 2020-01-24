package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.router.Router;

public class RedirectController extends Controller {

    public RedirectController(Router router, Request request) {
       super(router, request);
    }

    public Response get() {
        return new ExceptionsController().render301ResponseRedirectingTo("/simple_get");
    }
}
