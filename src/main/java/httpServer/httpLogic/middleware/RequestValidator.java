package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;

import java.util.HashSet;
import java.util.Set;

public class RequestValidator extends Middleware {

    private final Router router;
    private Request request;
    private Response response;
    private Set<String> blacklistedResources;

    public RequestValidator(Router router) {
        this.router = router;
        populateBlacklistedResources();
    }

    private void populateBlacklistedResources() {
        blacklistedResources = new HashSet<>();
        blacklistedResources.add("..");
    }

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        if (response.hasUndeterminedStatusCode()) {
            checkIfValidRequest();
        }
        passToNextMiddleware(request, response);
    }

    private void checkIfValidRequest() {
        if (request.wasUnparsable()) {
            response.statusCode = HTTPStatusCodes.BadRequest;
        } else if (requestHasUnrecognizedMethod()) {
            response.statusCode = HTTPStatusCodes.NotImplemented;
        } else if (pathRequestedContainsBlacklistedResource()) {
            response.statusCode = HTTPStatusCodes.NotFound;
        }
    }

    private boolean requestHasRecognizedMethod() {
        return router.getAllRecognizedHTTPMethods().contains(request.getHTTPMethod());
    }

    private boolean requestHasUnrecognizedMethod() {
        return !requestHasRecognizedMethod();
    }

    private boolean pathRequestedContainsBlacklistedResource() {
        String[] requestPaths = request.getPath().split("/");
        return pathRequestedContainsBlacklistedResource(requestPaths);
   }

    private boolean pathRequestedContainsBlacklistedResource(String[] requestPaths) {
        boolean pathBlacklisted = false;
        for (String path : requestPaths) {
            if (blacklistedResources.contains(path)) {
                pathBlacklisted = true;
            }
        }
        return pathBlacklisted;
    }

}
