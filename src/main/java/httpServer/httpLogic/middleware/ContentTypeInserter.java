package httpServer.httpLogic.middleware;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class ContentTypeInserter extends Middleware {

    @Override
    public void handle(Request request, Response response) {
        passToNextMiddleware(request, response);
    }

}
