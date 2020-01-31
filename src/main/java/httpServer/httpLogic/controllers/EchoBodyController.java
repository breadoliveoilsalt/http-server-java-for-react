package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class EchoBodyController extends Controller {

    public EchoBodyController(Request request, Response response) {
        super(request, response);
    }

    public Response post() {
       response.stringBody = request.getBody();
       return response;
    }

}
