package httpServer.httpLogic.handler;

import httpServer.httpLogic.constants.Methods;
import httpServer.httpLogic.controllers.ExceptionsController;
import httpServer.httpLogic.controllers.MetaDataRequestController;
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
            action = () -> MetaDataRequestController.buildHEADResponse(router, request);
        } else if (validOPTIONSRequest(request)) {

        } else if (hasUnrecognizedMethod(request)) {
            action = ExceptionsController::build501Response;
        } else {
            action = router.getActionFor(request.getPath(), request.getMethod());
        }

        return action.call();
    }

    private boolean validHEADRequest(Request request) {
        return request.getMethod().equals(Methods.HEAD) && router.getMethodsFor(request.getPath()).contains(Methods.GET);
    }

    private boolean validOPTIONSRequest(Request request) {
        return request.getMethod().equals(Methods.OPTIONS) && router.getPaths().contains(request.getPath());
    }

    private boolean hasUnrecognizedMethod(Request request) {
        return !router.getRecognizedMethods().contains(request.getMethod());
    }

}
