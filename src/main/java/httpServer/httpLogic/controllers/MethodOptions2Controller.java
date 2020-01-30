package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class MethodOptions2Controller extends Controller {

    public MethodOptions2Controller(Request request, Response response) {
        super(request, response);
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
