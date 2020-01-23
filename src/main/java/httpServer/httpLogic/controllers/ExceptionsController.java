package httpServer.httpLogic.controllers;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class ExceptionsController {

    public Response render400Response() {
        return new ResponseBuilder()
                .addStatusCode("400")
                .addStatusMessage("Bad Request")
                .addBody("400 Error: Bad Request Submitted")
                .build();
    }

    public Response render501Response() {
        return new ResponseBuilder()
                .addStatusCode("501")
                .addStatusMessage("Not Implemented")
                .addBody("501 Error: Method Not Implemented")
                .build();
    }

}
