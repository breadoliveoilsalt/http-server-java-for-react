package httpServer.httpLogic.controllers;

import httpServer.httpLogic.middleware.PublicFileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class RootPathController extends Controller {

    public RootPathController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        request.setPath("/index.html");
        new PublicFileFinder().handle(request, response);
        return response;
    }

}
