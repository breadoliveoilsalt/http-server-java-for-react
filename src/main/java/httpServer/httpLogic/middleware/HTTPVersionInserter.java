package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPVersions;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class HTTPVersionInserter extends Middleware {

    @Override
    public void handle(Request request, Response response) {
        response.httpVersion = HTTPVersions.versionImplemented;
        passToNextMiddleware(request, response);
    }
}
