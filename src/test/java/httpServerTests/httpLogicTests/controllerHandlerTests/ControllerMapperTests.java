//package httpServerTests.httpLogicTests.controllerHandlerTests;
//import httpServer.httpLogic.constants.HTTPMethods;
//import httpServer.httpLogic.constants.Whitespace;
//import httpServer.httpLogic.middleware.ControllerMapper;
//import httpServer.httpLogic.responses.ResponseBuilder;
//import httpServer.router.Router;
//import httpServer.httpLogic.requests.Request;
//import httpServer.httpLogic.requests.RequestBuilder;
//import httpServer.httpLogic.responses.Response;
//import httpServer.serverLogger.ServerLogger;
//import org.junit.Before;
//import org.junit.Test;
//import httpServerTests.httpLogicTests.controllerTests.PathOneTestController;
//import httpServerTests.httpLogicTests.controllerTests.TestRouterFactory;
//
//import java.io.ByteArrayOutputStream;
//import java.io.OutputStream;
//
//import static org.junit.Assert.*;
//
//public class ControllerMapperTests {
//
//    Router router;
//    String pathOne;
//    ServerLogger logger;
//    OutputStream loggerOutputStream;
//    ControllerMapper controllerMapper;
//
//    @Before
//    public void testInit() {
//        pathOne = "/path_one";
//        router = TestRouterFactory.buildWithPathOneAndPathTwoControllers();
//        PathOneTestController.getResponseToReturn = null;
//        loggerOutputStream = new ByteArrayOutputStream();
//        logger = new ServerLogger(loggerOutputStream);
//        controllerMapper = new ControllerMapper(router, logger);
//    }
//
//    @Test
//    public void handleReliesOnARouterToMatchThePathAndHTTPMethodOfAClientRequestToAControllerAndMethodToReturnAResponse() throws Exception {
//        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.GET).build();
//        Response expectedResponse = new ResponseBuilder().build();
//        PathOneTestController.getResponseToReturn = expectedResponse;
//
//        Response result = new ControllerMapper(router, logger).handle(clientRequest);
//
//        assertEquals(expectedResponse, result);
//    }
//    @Test
//    public void handleReturnsA405MethodNotAllowedResponseIfAMethodIsRecognizedByTheServerButTheRequestedResourceDoesNotImplementTheRequestedMethod() throws Exception {
//        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.PUT).build();
//
//        Response result = new ControllerMapper(router, logger).handle(clientRequest);
//
//        assertEquals("405", result.getStatusCode());
//        assertEquals("Method Not Allowed", result.getStatusMessage());
//    }

//    @Test
//    public void handleCausesTheLoggerToLogAMessageAboutTheRequestAndResponse() throws Exception {
//        Request clientRequest = new RequestBuilder().addPath(pathOne).addMethod(HTTPMethods.GET).addHeader("Host", "000.000.000").build();
//        Response response = new ResponseBuilder().addStatusCode("200").build();
//        PathOneTestController.getResponseToReturn = response;
//
//        Response result = new ControllerMapper(router, logger).handle(clientRequest);
//
//        String expectedLog =
//                Whitespace.DIVIDER +
//                Whitespace.CRLF +
//                "000.000.000 made a GET request to /path_one." +
//                Whitespace.CRLF +
//                "The server responded with a 200 status code." +
//                Whitespace.CRLF +
//                Whitespace.DIVIDER;
//
//        assertEquals(expectedLog, loggerOutputStream.toString());
//    }
//}
