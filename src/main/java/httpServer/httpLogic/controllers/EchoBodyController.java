package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

import java.io.UnsupportedEncodingException;


public class EchoBodyController {

    private Router router;
    private Request request;

    public EchoBodyController(Router router, Request request) {
        this.router = router;
        this.request = request;
    }

    public Response post() throws UnsupportedEncodingException {
        String responseBody = request.getBody();
        return new ResponseBuilder()
                .addBody(responseBody)
                .finalizeMetaDataForOKResponse()
                .build();
    }

}

// PRIOR WORKING VERSION
//public class EchoBodyController implements Controller {
//
//    public static Response post(Request request) throws UnsupportedEncodingException {
//        String responseBody = request.getBody();
//        return new ResponseBuilder()
//                .addBody(responseBody)
//                .finalizeMetaDataForOKResponse()
//                .build();
//    }
//}
