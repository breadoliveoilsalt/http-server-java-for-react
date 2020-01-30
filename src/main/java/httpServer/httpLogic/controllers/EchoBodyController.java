package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class EchoBodyController extends Controller {

    public EchoBodyController(Request request, Response response) {
        super(request, response);
    }

    public Response post() {
        String responseBody = request.getBody();
        return new ResponseBuilder()
                .addBody(responseBody)
                .finalizeMetaDataForOKResponse()
                .build();
    }

}
