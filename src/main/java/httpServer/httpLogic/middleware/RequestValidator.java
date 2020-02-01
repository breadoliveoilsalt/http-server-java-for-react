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
        if (response.hasUndeterminedStatus()) {
            checkIfValidRequest();
        }
        passToNextMiddleware(request, response);
    }

    private void checkIfValidRequest() {
        if (request.wasUnparsable()) {
            response.statusCode = HTTPStatusCodes.BadRequest;
            return;
        }

        if (requestHasUnrecognizedMethod()) {
            response.statusCode = HTTPStatusCodes.NotImplemented;
            return;
        }

        if (requestedResourceDoesNotExist()) {
            response.statusCode = HTTPStatusCodes.NotFound;
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
