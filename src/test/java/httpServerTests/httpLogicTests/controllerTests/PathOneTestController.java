package httpServerTests.httpLogicTests.controllerTests;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.router.Router;

public class PathOneTestController extends Controller {

    public static Response getResponseToReturn;

    public PathOneTestController(Router router, Request request) {
        super(router, request);
    }

    public Response get() {
        return getResponseToReturn;
    }

}
