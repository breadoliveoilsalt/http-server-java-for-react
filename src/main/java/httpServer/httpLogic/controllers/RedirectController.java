package httpServer.httpLogic.controllers;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class RedirectController extends Controller {

    public RedirectController(Request request, Response response) {
       super(request, response);
    }

    public Response get() {
        response.statusCode = HTTPStatusCodes.MovedPermanently;
        response.addHeader("Location", "http://127.0.0.1:5000" + "/simple_get");
        return response;
    }
}
