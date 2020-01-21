package httpServer.httpLogic.handler;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseFactory;
import httpServer.httpLogic.router.Router;

import java.util.concurrent.Callable;

public class Handler {

    private final Router router;

    public Handler(Router router) {
        this.router = router;
    }

    public Response handle(Request request) throws Exception {
        Response responseToReturn;
        if (request.isInvalid()) {
            responseToReturn = ResponseFactory.build400Response();
        } else if (hasUnrecognizedMethod(request)) {
            responseToReturn = ResponseFactory.build501Response();
        } else if (validHEADRequest(request)) {
            Callable<Response> action = router.getActionFor(request.getPath(), "GET");
            Response fullResponse = action.call();
            responseToReturn = ResponseFactory.buildHEADResponseFor(fullResponse);
        } else {
            Callable<Response> action = router.getActionFor(request.getPath(), request.getMethod());
            responseToReturn = action.call();
        }
        return responseToReturn;
    }

    private boolean hasUnrecognizedMethod(Request request) {
        return !router.getRecognizedMethods().contains(request.getMethod());
    }

    private boolean validHEADRequest(Request request) {
        return request.getMethod().equals("HEAD") && router.getMethodsFor(request.getPath()).contains("GET");
    }


}
