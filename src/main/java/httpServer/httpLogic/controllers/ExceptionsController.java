package httpServer.httpLogic.controllers;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

public class ExceptionsController {

    public Response render405Response(Controller controller) {
        String recognizedMethods = controller.getStringOfRecognizedMethods();
        return new ResponseBuilder()
                .addStatusCode("405")
                .addHeader("Allow", recognizedMethods)
                .addStatusMessage("Method Not Allowed")
                .addContentLength()
                .build();
    }

}
