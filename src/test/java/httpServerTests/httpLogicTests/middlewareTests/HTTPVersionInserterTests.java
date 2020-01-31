package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPVersions;
import httpServer.httpLogic.middleware.HTTPVersionInserter;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HTTPVersionInserterTests {

    @Test
    public void handleAddsTheHTTPVersionImplementedToTheResponse() {
        Request placeholderRequest = new RequestBuilder().build();
        Response response = new Response();

        assertNull(response.httpVersion);
        new HTTPVersionInserter().handle(placeholderRequest, response);

        assertEquals(HTTPVersions.versionImplemented, response.httpVersion);
    }
}
