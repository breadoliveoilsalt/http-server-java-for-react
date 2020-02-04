package httpServer.httpLogic.middlewareConfig;

import httpServer.httpLogic.middleware.*;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;

public class ResponseBuildingMiddleware {

    public void runWithBasicConfig(Router router, Request request, Response response) {
        Middleware middlewareHead = new RequestValidator(router);

        middlewareHead
            .setNext(new FileFinder())
            .setNext(new ResourcePathValidator(router))
            .setNext(new ControllerMapper(router))
            .setNext(new ResourceFoundValidator())
            .setNext(new HTTPVersionInserter())
            .setNext(new HTTPStatusMessageInserter())
            .setNext(new ContentLengthInserter())
            .setNext(new ContentTypeInserter());

        middlewareHead.handle(request, response);
    }
}
