package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;

public class RequestValidator extends Middleware {

    private Router router;
    private Request request;
    private Response response;

    public RequestValidator(Router router) {
        this.router = router;
    }

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        checkIfValidRequest();
        passToNextMiddleware(request, response);
    }

    private void checkIfValidRequest() {
        if (request.wasUnparsable()) {
            response.statusCode = HTTPStatusCodes.BadRequest;
//            response.statusMessage = "Bad Request";
//            response.stringBody = "400 Error: Bad Request Submitted";
            return;
        }

        if (requestHasUnrecognizedMethod()) {
            response.statusCode = HTTPStatusCodes.NotImplemented;
//            response.statusMessage = "Not Implemented";
//            response.stringBody = "501 Error: Method Not Implemented";
            return;
        }

        // MUST MODIFY THIS CONDITIONAL ONCE I GET CONTROLLER TOGETHER - delete the first check for /
        if (!request.getPath().equals("/") && requestedResourceDoesNotExist()) {
            response.statusCode = HTTPStatusCodes.NotFound;
//            response.statusMessage = "Not Found";
            return;
        }
    }

    private boolean requestHasRecognizedMethod() {
        return router.getAllRecognizedHTTPMethods().contains(request.getHTTPMethod());
    }

    private boolean requestHasUnrecognizedMethod() {
        return !requestHasRecognizedMethod();
    }

    private boolean requestedResourceExists() {
        return router.getPaths().contains(request.getPath());
    }

    private boolean requestedResourceDoesNotExist() {
        return !requestedResourceExists();
    }
}
