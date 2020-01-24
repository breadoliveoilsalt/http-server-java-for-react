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

    public Response render405Response(Controller controller) {
        String recognizedMethods = controller.getStringOfRecognizedMethods();
        return new ResponseBuilder()
                .addStatusCode("405")
                .addHeader("Allow", recognizedMethods)
                .addStatusMessage("Method Not Allowed")
                .build();
    }

    public Response render301ResponseRedirectingTo(String newPath) {
        return new ResponseBuilder()
                .addStatusCode("301")
                .addHeader("Location", "http://127.0.0.1:5000" + newPath)
                .addStatusMessage("Moved Permanently")
                .addContentLength()
                .build();
    }

}
