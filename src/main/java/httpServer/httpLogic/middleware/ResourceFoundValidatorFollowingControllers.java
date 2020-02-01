package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class ResourceFoundValidatorFollowingControllers extends Middleware {

    @Override
    public void handle(Request request, Response response) {
       if (response.hasUndeterminedStatus()) {
           response.statusCode = HTTPStatusCodes.NotFound;
       }
    }
}
