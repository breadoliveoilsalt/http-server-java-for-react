package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.router.Router;

public class EchoBodyController extends Controller {

    public EchoBodyController(Router router, Request request) {
        super(router, request);
    }

    public Response post() {
        String responseBody = request.getBody();
        return new ResponseBuilder()
                .addBody(responseBody)
                .finalizeMetaDataForOKResponse()
                .build();
    }

}
