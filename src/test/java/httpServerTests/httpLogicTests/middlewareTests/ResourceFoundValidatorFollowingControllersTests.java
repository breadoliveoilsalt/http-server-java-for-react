package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.ResourceFoundValidatorFollowingControllers;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceFoundValidatorFollowingControllersTests {

    @Test
    public void handleAddsANotFoundStatusCodeIfTheResponseDoesNotHaveAStatusCode() {
        Request request = new RequestBuilder().build();
        Response response = new Response();

        assertNull(response.statusCode);
        new ResourceFoundValidatorFollowingControllers().handle(request, response);

        assertEquals(HTTPStatusCodes.NotFound, response.statusCode);
    }

}
