package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

public class HeadRequestController {

    public static Response buildHEADResponse(Router router, Request request) throws Exception {
        Response fullResponse = router.getActionFor(request.getPath(), "GET").call();

        return new ResponseBuilder()
                .addOKStatusLine()
                .setHeaders(fullResponse.getHeaders())
                .build();
    }

}
