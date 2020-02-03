package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class ContentTypeInserter extends Middleware {

    private Response response;

    @Override
    public void handle(Request request, Response response) {
        this.response = response;
        if (responseHasOKStatusCode()) {
            if (response.stringBody != null) {
                addTextPlainContentType();
            }
        }
        passToNextMiddleware(request, response);
    }

    private boolean responseHasOKStatusCode() {
        return response.statusCode.equals(HTTPStatusCodes.OK);
    }

    private void addTextPlainContentType() {
        response.addHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextPlain);
    }

}
