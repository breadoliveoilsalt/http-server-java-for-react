package httpServer.httpLogic.handler;

import httpServer.httpLogic.controllers.ExceptionsController;
import httpServer.httpLogic.controllers.HeadRequestController;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.router.Router;

import java.util.concurrent.Callable;

public class Handler {

    private final Router router;

    public Handler(Router router) {
        this.router = router;
    }

    public Response handle(Request request) throws Exception {
        Callable<Response> action;

        if (request.isInvalid()) {
            action = ExceptionsController::build400Response;
        } else if (validHEADRequest(request)) {
            action = () -> HeadRequestController.buildHEADResponse(router, request);
        } else if (hasUnrecognizedMethod(request)) {
            action = ExceptionsController::build501Response;
        } else {
            action = router.getActionFor(request.getPath(), request.getMethod());
        }

        return action.call();
    }

    private boolean hasUnrecognizedMethod(Request request) {
        return !router.getRecognizedMethods().contains(request.getMethod());
    }

    private boolean validHEADRequest(Request request) {
        return request.getMethod().equals("HEAD") && router.getMethodsFor(request.getPath()).contains("GET");
    }


}
