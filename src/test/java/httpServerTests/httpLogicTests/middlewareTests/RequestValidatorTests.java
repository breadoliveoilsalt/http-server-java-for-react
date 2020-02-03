package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.RequestValidator;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;
import httpServerTests.httpLogicTests.testRouterAndControllers.TestPaths;
import httpServerTests.httpLogicTests.testRouterAndControllers.TestRouterFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestValidatorTests {

    private RequestValidator requestValidator;
    private Request request;
    private Response response;

    @Before
    public void testInit() {
        Router testRouter = TestRouterFactory.buildWithPathOneAndPathTwoTestControllers();
        requestValidator = new RequestValidator(testRouter);
        response = new Response();
    }

    @Test
    public void handleAddsABadRequestStatusCodeToARequestIfARequestIsFlaggedAsUnparsable() {
        request = new RequestBuilder().flagAsUnparsable().build();

        assertNull(response.statusCode);

        requestValidator.handle(request, response);

        assertEquals(HTTPStatusCodes.BadRequest, response.statusCode);
    }


    @Test
    public void handleAddsANotImplementedStatusCodeWhenTheRouterDoesNotRecognizeTheMethodRequestedByTheClient() {
        request = new RequestBuilder().addPath(TestPaths.pathOne).addMethod("BANANAS").build();

        assertNull(response.statusCode);

        requestValidator.handle(request, response);

        assertEquals(HTTPStatusCodes.NotImplemented, response.statusCode);
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        request = new RequestBuilder().build();
        MockMiddleware nextMiddleware = new MockMiddleware();
        requestValidator.setNext(nextMiddleware);

        requestValidator.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

}
