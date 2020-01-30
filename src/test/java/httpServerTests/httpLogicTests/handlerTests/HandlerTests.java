package httpServerTests.httpLogicTests.handlerTests;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.handler.Handler;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.router.Router;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.serverLogger.ServerLogger;
import org.junit.Before;
import org.junit.Test;
import httpServerTests.httpLogicTests.controllerTests.PathOneTestController;
import httpServerTests.httpLogicTests.controllerTests.TestRouterFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class HandlerTests {

    Router router;
    String pathOne;
    ServerLogger logger;
    OutputStream loggerOutputStream;
    Handler handler;

    @Before
    public void testInit() {
        pathOne = "/path_one";
        router = TestRouterFactory.buildWithPathOneAndPathTwoControllers();
        PathOneTestController.getResponseToReturn = null;
        loggerOutputStream = new ByteArrayOutputStream();
        logger = new ServerLogger(loggerOutputStream);
        handler = new Handler(router, logger);
    }

    @Test
    public void handleReliesOnARouterToMatchThePathAndHTTPMethodOfAClientRequestToAControllerAndMethodToReturnAResponse() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.GET).build();
        Response expectedResponse = new ResponseBuilder().build();
        PathOneTestController.getResponseToReturn = expectedResponse;

        Response result = new Handler(router, logger).handle(clientRequest);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void handleReturnsA501ResponseWhenTheRouterDoesNotRecognizeTheMethod() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod("BANANAS").build();

        Response result = new Handler(router, logger).handle(clientRequest);

        assertEquals("501", result.getStatusCode());
        assertEquals("Not Implemented", result.getStatusMessage());
        assertEquals("501 Error: Method Not Implemented", result.getStringBody());
    }

    @Test
    public void handleReturnsA400BadRequestResponseIfARequestIsFlaggedAsUnparsable() throws Exception {
        Request clientRequest = new RequestBuilder().flagAsUnparsable().build();

        Response result = new Handler(router, logger).handle(clientRequest);

        assertEquals("400", result.getStatusCode());
        assertEquals("Bad Request", result.getStatusMessage());
        assertEquals("400 Error: Bad Request Submitted", result.getStringBody());
    }

    @Test
    public void handleReturnsA405MethodNotAllowedResponseIfAMethodIsRecognizedByTheServerButTheRequestedResourceDoesNotImplementTheRequestedMethod() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.PUT).build();

        Response result = new Handler(router, logger).handle(clientRequest);

        assertEquals("405", result.getStatusCode());
        assertEquals("Method Not Allowed", result.getStatusMessage());
    }

    @Test
    public void handleReturnsA404NotFoundResponseIfTheAMethodIsRecognizedByTheServerButTheResourceDoesNotExist() throws Exception {
        Request clientRequest = new RequestBuilder().addPath("/non_existent_path").addMethod(HTTPMethods.GET).build();

        Response result = new Handler(router, logger).handle(clientRequest);

        assertEquals("404", result.getStatusCode());
        assertEquals("Not Found", result.getStatusMessage());
    }

    @Test
    public void handleCausesTheLoggerToLogAMessageAboutTheRequestAndResponse() throws Exception {
        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.GET).addHeader("Host", "000.000.000").build();
        Response response = new ResponseBuilder().addStatusCode("200").build();
        PathOneTestController.getResponseToReturn = response;

        Response result = new Handler(router, logger).handle(clientRequest);

        String expectedLog =
                Whitespace.DIVIDER +
                Whitespace.CRLF +
                "000.000.000 made a GET request to /path_one." +
                Whitespace.CRLF +
                "The server responded with a 200 status code." +
                Whitespace.CRLF +
                Whitespace.DIVIDER;

        assertEquals(expectedLog, loggerOutputStream.toString());
    }
}
