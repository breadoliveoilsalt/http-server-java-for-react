package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

public class GetWithBodyController {

    private Router router;
    private Request request;

    public GetWithBodyController(Router router, Request request) {
        this.router = router;
        this.request = request;
    }

    public Response get() {
        return new ResponseBuilder()
            .addBody("Hello World!")
            .finalizeMetaDataForOKResponse()
            .build();
    }
}
