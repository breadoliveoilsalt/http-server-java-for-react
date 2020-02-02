package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.ResourceFoundValidator;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceFoundValidatorTests {

    @Test
    public void handleAddsANotFoundStatusCodeIfTheClientMadeAGETRequestAndAControllerAssignedAnOKStatusCodeToTheResponseWithoutAssigningAStringBodyOrFileToTheResponse() {
        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).build();
        Response response = new Response();
        response.statusCode = HTTPStatusCodes.OK;

        new ResourceFoundValidator().handle(request, response);

        assertEquals(HTTPStatusCodes.NotFound, response.statusCode);
    }

}
