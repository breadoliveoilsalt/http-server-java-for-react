package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class GetWithBodyController extends Controller {

    public GetWithBodyController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        return new ResponseBuilder()
               .addBody("Hello World!")
               .finalizeMetaDataForOKResponse()
               .build();
    }

}
