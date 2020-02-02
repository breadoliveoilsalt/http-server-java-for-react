package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class ResourceFoundValidator extends Middleware {

    @Override
    public void handle(Request request, Response response) {
       if (response.hasUndeterminedStatus()) {
           response.statusCode = HTTPStatusCodes.NotFound;
       }
       passToNextMiddleware(request, response);
    }
}
