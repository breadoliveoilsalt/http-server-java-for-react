package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;

public class ResourcePathValidator extends Middleware {

    private final Router router;
    private Request request;

    public ResourcePathValidator(Router router) {
        this.router = router;
    }

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        if (response.hasUndeterminedStatus() && requestedPathDoesNotExist()) {
            response.statusCode = HTTPStatusCodes.NotFound;
        }
        passToNextMiddleware(request, response);

    }

    private boolean requestedPathExists() {
        return router.getPaths().contains(request.getPath());
    }

    private boolean requestedPathDoesNotExist() {
        return !requestedPathExists();
    }

}
