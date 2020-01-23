package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

public class MethodOptions2Controller extends Controller {

    public MethodOptions2Controller(Router router, Request request) {
        super(router, request);
    }

    public Response get() {
        return new ResponseBuilder()
                .finalizeMetaDataForOKResponse()
                .build();
    }

    public Response put() {
        return new ResponseBuilder()
                .finalizeMetaDataForOKResponse()
                .build();
    }

    public Response post() {
        return new ResponseBuilder()
                .finalizeMetaDataForOKResponse()
                .build();
    }

}
