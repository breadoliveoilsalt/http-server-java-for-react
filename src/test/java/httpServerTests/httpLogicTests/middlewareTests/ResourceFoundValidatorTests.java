package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.ResourceFoundValidator;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResourceFoundValidatorTests {

    private Request request;
    private Response response;
    private ResourceFoundValidator resourceFoundValidator;

    @Before
    public void testInit() {
        request = new RequestBuilder().addMethod(HTTPMethods.GET).build();
        response = new Response();
        resourceFoundValidator = new ResourceFoundValidator();
    }

    @Test
    public void handleAddsANotFoundStatusCodeIfTheClientMadeAGETRequestAndAControllerAssignedAnOKStatusCodeToTheResponseWithoutAssigningAStringBodyOrFileToTheResponse() {
        response.statusCode = HTTPStatusCodes.OK;

        resourceFoundValidator.handle(request, response);

        assertEquals(HTTPStatusCodes.NotFound, response.statusCode);
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        MockMiddleware nextMiddleware = new MockMiddleware();
        resourceFoundValidator.setNext(nextMiddleware);
        response.statusCode = HTTPStatusCodes.OK;

        resourceFoundValidator.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

}
