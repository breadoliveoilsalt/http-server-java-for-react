package unitTests.tests.httpLogic.controllerTests;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;

public class TestController extends Controller {

    public static Response getResponseToReturn;

    public TestController(Router router, Request request) {
        super(router, request);
    }

    public Response get() {
        return getResponseToReturn;
//        return new ResponseBuilder().build();
    }

}
