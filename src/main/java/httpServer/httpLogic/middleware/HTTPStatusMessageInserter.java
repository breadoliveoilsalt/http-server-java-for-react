package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.constants.HTTPStatusMessages;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class HTTPStatusMessageInserter extends Middleware {

    private Response response;

    @Override
    public void handle(Request request, Response response) {
        this.response = response;
        addStatusMessage();
        passToNextMiddleware(request, response);
    }

    private void addStatusMessage() {
        switch (response.statusCode) {
            case HTTPStatusCodes.OK:
                response.statusMessage = HTTPStatusMessages.OK;
                break;
            case HTTPStatusCodes.MovedPermanently:
                response.statusMessage = HTTPStatusMessages.MovedPermanently;
                break;
            case HTTPStatusCodes.BadRequest:
                response.statusMessage = HTTPStatusMessages.BadRequest;
                break;
            case HTTPStatusCodes.NotFound:
                response.statusMessage = HTTPStatusMessages.NotFound;
                break;
            case HTTPStatusCodes.MethodNotAllowed:
                response.statusMessage = HTTPStatusMessages.MethodNotAllowed;
                break;
            case HTTPStatusCodes.NotImplemented:
                response.statusMessage = HTTPStatusMessages.NotImplemented;
                break;
            case HTTPStatusCodes.RequestTimeout:
                response.statusMessage = HTTPStatusMessages.RequestTimeout;
                break;
        }
    }
}
