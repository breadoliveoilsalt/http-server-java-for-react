package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class ResourceFoundValidator extends Middleware {

    private Request request;
    private Response response;

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        if (getRequestMade() && controllerAcceptedRequestWithoutAssigningAResource()) {
           response.statusCode = HTTPStatusCodes.NotFound;
       }
       passToNextMiddleware(request, response);
    }

    private boolean getRequestMade() {
        return request.getHTTPMethod() != null && request.getHTTPMethod().equals(HTTPMethods.GET);
    }

    private boolean controllerAcceptedRequestWithoutAssigningAResource() {
        return response.statusCode.equals(HTTPStatusCodes.OK) && response.stringBody == null && response.file == null;
    }

}
