package unitTests.tests.httpLogic.controllerTests;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

public class PathTwoTestController extends Controller {

    public PathTwoTestController(Router router, Request request) {
        super(router, request);
    }

    public Response put() {
        return new ResponseBuilder().build();
    }
}
