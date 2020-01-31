package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class SimpleGetController extends Controller {

    public SimpleGetController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        return response;
//        return new ResponseBuilder()
//                .finalizeMetaDataForOKResponse()
//                .build();
    }

}
