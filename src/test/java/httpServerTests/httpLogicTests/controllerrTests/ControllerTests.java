package httpServerTests.httpLogicTests.controllerrTests;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServerTests.httpLogicTests.testRouterAndControllers.PathOneTestController;
import httpServerTests.httpLogicTests.testRouterAndControllers.PathTwoTestController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerTests {

    private Request request;
    private Response response;

    @Before
    public void testInit() {
        response = new Response();
        request = new RequestBuilder().build();
    }

    @Test
    public void headTreatsTheRequestAsIfItWereAGetRequest() {
        String testMessage = "Test message";
        PathOneTestController.stringBodyToAddForTest = testMessage;
        new PathOneTestController(request, response).head();

        assertEquals(HTTPStatusCodes.OK, response.statusCode);
        assertEquals(testMessage, response.stringBody);
    }

    @Test
    public void headAddsAMethodNotAllowedStatusCodeIfTheControllerHasNoGetMethod() {
        new PathTwoTestController(request, response).head();

        assertEquals(HTTPStatusCodes.MethodNotAllowed, response.statusCode);
    }

    @Test
    public void headAddsAHeaderWithAllowedMethodsIfTheControllerHasNoGetMethod() {
        new PathTwoTestController(request, response).head();

        assertTrue(response.hasHeader(HTTPHeaders.Allow, "HEAD, OPTIONS, PUT"));
    }

    @Test
            public void optionsPopulatesAResponseWithHTTPMethodsAvailableForTheGivenResource() {
        new PathOneTestController(request, response).options();

        assertEquals(HTTPStatusCodes.OK, response.statusCode);
        assertTrue(response.hasHeader(HTTPHeaders.Allow, "HEAD, GET, OPTIONS"));
    }

}
