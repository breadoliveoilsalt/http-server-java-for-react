package unitTests.tests.httpLogic.controllerTests;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.router.Router;

public class PathTwoTestController extends Controller {

    public PathTwoTestController(Router router, Request request) {
        super(router, request);
    }
}
