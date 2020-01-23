package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

public class MethodOptionsController extends Controller {

    public MethodOptionsController(Router router, Request request) {
        super(router, request);
    }

    public Response get() {
        return new ResponseBuilder()
                .finalizeMetaDataForOKResponse()
                .build();
    }

}
