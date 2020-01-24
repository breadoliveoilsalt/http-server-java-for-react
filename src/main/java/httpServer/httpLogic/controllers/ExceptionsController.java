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

    public Response render405Response() {
        return new ResponseBuilder()
                .addStatusCode("405")
                .addStatusMessage("Method Not Allowed")
                .addBody("405 Error: Method is known by the server but is not supported by the target resource")
                .build();
    }
}
