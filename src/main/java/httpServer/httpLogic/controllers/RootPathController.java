package httpServer.httpLogic.controllers;

import httpServer.httpLogic.middleware.FileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class RootPathController extends Controller {

    public RootPathController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        request.setPath("/index.html");
        new FileFinder().handle(request, response);
        return response;
    }

}
