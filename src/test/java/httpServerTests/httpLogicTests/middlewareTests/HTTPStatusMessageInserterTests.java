package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.constants.HTTPStatusMessages;
import httpServer.httpLogic.middleware.HTTPStatusMessageInserter;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HTTPStatusMessageInserterTests {

    Request placeHolderRequest;
    Response response;
    HTTPStatusMessageInserter messageInserter;

    @Before
    public void testInit() {
        placeHolderRequest = new RequestBuilder().build();
        response = new Response();
        messageInserter = new HTTPStatusMessageInserter();
    }

    @Test
    public void handleAddsAStatusMessageToTheResponse() {
        response.statusCode = HTTPStatusCodes.OK;

        assertNull(response.statusMessage);
        messageInserter.handle(placeHolderRequest, response);

        assertEquals(HTTPStatusMessages.OK, response.statusMessage);
    }

    @Test
    public void handleAddsAStatusMessageMappedToAStatusCode() {
        response.statusCode = HTTPStatusCodes.BadRequest;
        messageInserter.handle(placeHolderRequest, response);
        assertEquals(HTTPStatusMessages.BadRequest, response.statusMessage);

        response.statusCode = HTTPStatusCodes.MethodNotAllowed;
        messageInserter.handle(placeHolderRequest, response);
        assertEquals(HTTPStatusMessages.MethodNotAllowed, response.statusMessage);

        response.statusCode = HTTPStatusCodes.NotFound;
        messageInserter.handle(placeHolderRequest, response);
        assertEquals(HTTPStatusMessages.NotFound, response.statusMessage);
    }
}
