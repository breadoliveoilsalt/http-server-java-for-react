package httpServerTests.httpLogicTests.testRouterAndControllers;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class PathTwoTestController extends Controller {

    public PathTwoTestController(Request request, Response response) {
        super(request, response);
    }

    public Response put() {
        return response;
    }
}
