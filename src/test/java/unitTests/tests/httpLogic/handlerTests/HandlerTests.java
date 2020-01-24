package unitTests.tests.httpLogic.handlerTests;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.handler.Handler;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;
import unitTests.tests.httpLogic.controllerTests.PathOneTestController;
import unitTests.tests.httpLogic.controllerTests.TestRouterFactory;

import static org.junit.Assert.*;

public class HandlerTests {

    Router router;
    String pathOne;

    @Before
    public void testInit() {
        pathOne = "/path_one";
        router = TestRouterFactory.buildWithPathOneAndPathTwoControllers();
        PathOneTestController.getResponseToReturn = null;
    }

    @Test
    public void handleReliesOnARouterToMatchThePathAndHTTPMethodOfAClientRequestToAControllerAndMethodToReturnAResponse() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.GET).build();
        Response expectedResponse = new ResponseBuilder().build();
        PathOneTestController.getResponseToReturn = expectedResponse;

        Response result = new Handler(router).handle(clientRequest);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void handleReturnsA501ResponseWhenTheRouterDoesNotRecognizeTheMethod() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod("BANANAS").build();

        Response result = new Handler(router).handle(clientRequest);

        assertEquals("501", result.getStatusCode());
        assertEquals("Not Implemented", result.getStatusMessage());
        assertEquals("501 Error: Method Not Implemented", result.getBody());
    }

    @Test
    public void handleReturnsA400BadRequestResponseIfARequestIsFlaggedAsUnparsable() throws Exception {
        Request clientRequest = new RequestBuilder().flagAsUnparsable().build();

        Response result = new Handler(router).handle(clientRequest);

        assertEquals("400", result.getStatusCode());
        assertEquals("Bad Request", result.getStatusMessage());
        assertEquals("400 Error: Bad Request Submitted", result.getBody());
    }

    @Test
    public void handleReturnsA405MethodNotAllowedResponseIfAMethodIsRecognizedByTheServerButTheRequestedResourceDoesNotImplementTheRequestedMethod() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.PUT).build();

        Response result = new Handler(router).handle(clientRequest);

        assertEquals("405", result.getStatusCode());
        assertEquals("Method Not Allowed", result.getStatusMessage());
    }
}
