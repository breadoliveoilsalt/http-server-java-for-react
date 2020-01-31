package httpServerTests.httpLogicTests.middlewareTests;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.middleware.ControllerMapper;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.router.Router;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.serverLogger.ServerLogger;
import httpServerTests.httpLogicTests.testRouterAndControllers.PathOneTestController;
import httpServerTests.httpLogicTests.testRouterAndControllers.TestPaths;
import httpServerTests.httpLogicTests.testRouterAndControllers.TestRouterFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class ControllerMapperTests {

    Router router;
    ControllerMapper controllerMapper;
    Request request;
    Response response;

    @Before
    public void testInit() {
        response = new Response();
        router = TestRouterFactory.buildWithPathOneAndPathTwoTestControllers();
        controllerMapper = new ControllerMapper(router);
        PathOneTestController.stringBodyToAddForTest = null;
    }

    @Test
    public void handleReliesOnARouterToMatchThePathAndHTTPMethodOfAClientRequestToAControllerAndMethodToManipulateAReturnAResponse() {
        request = new RequestBuilder().addPath(TestPaths.pathOne).addMethod(HTTPMethods.GET).build();
        String testBody = "Test Body";
        PathOneTestController.stringBodyToAddForTest = testBody;

        assertNull(response.stringBody);
        controllerMapper.handle(request, response);

        assertEquals(testBody, response.stringBody);
    }

    @Test
    public void handleAddsAnOKStatusCodeIfItCanConnectAPathAndMethodRequestedToAControllerAndMethod() {
        request = new RequestBuilder().addPath(TestPaths.pathOne).addMethod(HTTPMethods.GET).build();

        assertNull(response.statusCode);
        controllerMapper.handle(request, response);

        assertEquals(HTTPStatusCodes.OK, response.statusCode);
    }

    @Test
    public void handleAddsAMethodNotAllowedResponseIfAMethodIsRecognizedByTheServerButTheRequestedResourceDoesNotImplementTheRequestedMethod() {
        request = new RequestBuilder().addPath(TestPaths.pathOne).addMethod(HTTPMethods.PUT).build();

        assertNull(response.statusCode);
        controllerMapper.handle(request, response);

        assertEquals(HTTPStatusCodes.MethodNotAllowed, response.statusCode);
    }

}
