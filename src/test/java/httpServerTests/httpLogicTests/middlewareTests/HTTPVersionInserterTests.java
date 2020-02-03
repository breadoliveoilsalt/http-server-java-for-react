package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPVersions;
import httpServer.httpLogic.middleware.HTTPVersionInserter;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPVersionInserterTests {

    private HTTPVersionInserter httpVersionInserter;
    private Request placeholderRequest;
    private Response response;

    @Before
    public void testInit() {
        placeholderRequest = new RequestBuilder().build();
        response = new Response();
        httpVersionInserter = new HTTPVersionInserter();
    }

    @Test
    public void handleAddsTheHTTPVersionImplementedToTheResponse() {
        assertNull(response.httpVersion);
        httpVersionInserter.handle(placeholderRequest, response);

        assertEquals(HTTPVersions.versionImplemented, response.httpVersion);
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        MockMiddleware nextMiddleware = new MockMiddleware();
        httpVersionInserter.setNext(nextMiddleware);

        httpVersionInserter.handle(placeholderRequest, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

}
