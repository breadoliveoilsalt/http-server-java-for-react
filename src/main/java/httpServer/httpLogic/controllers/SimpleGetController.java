package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

import java.io.UnsupportedEncodingException;

public class SimpleGetController {

    private Router router;
    private Request request;

    public SimpleGetController(Router router, Request request) {
        this.router = router;
        this.request = request;
    }

    public Response get() throws UnsupportedEncodingException {
        return new ResponseBuilder()
                .finalizeMetaDataForOKResponse()
//                .addOKStatusLine()
                .build();
    }

}
