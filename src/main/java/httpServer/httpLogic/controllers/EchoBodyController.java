package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;

import java.io.UnsupportedEncodingException;

public class EchoBodyController {

    public static Response post(Request request) throws UnsupportedEncodingException {
        String responseBody = request.getBody();
        return new ResponseBuilder()
                .addBody(responseBody)
                .finalizeMetaDataForOKResponse()
                .build();
    }
}
