package unitTests.tests.httpLogic.controllerLogicTests;

import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class HandlerTests {

    private Router router;
    private Request clientRequest;
    private Response genericResponse;

    @Before
    public void testInit() {
        buildGenericResponse();
    }

    private void buildGenericResponse() {
        genericResponse = new ResponseBuilder()
            .addStatusCode("200")
            .addStatusMessage("OK")
            .addHeader("Date", "Some day")
            .addHeader("Content-Length", "Some length")
            .addBody("Some message")
            .build();
    }

    @Test
    public void handleCausesAnActionSpecifiedByTheControllerToCreateAResponse() throws Exception {
        String path = "/some_path";
        String method = "GET";
        setControllerForHandleActionTest(path, method, () -> returnGenericResponse());
        clientRequest = new RequestBuilder().addPath(path).addMethod(method).build();

        Response result = controller.handle(clientRequest);

        assertEquals(genericResponse, result);
    }

    @Test
    public void handleReturnsAResponseWithOnlyAPathsMetaDataInResponseToAHEADRequest() throws Exception {
        String path = "/some_path";
        setControllerForHandleActionTest(path, "GET", () -> returnGenericResponse());
        clientRequest = new RequestBuilder().addPath(path).addMethod("HEAD").build();

        Response result = controller.handle(clientRequest);

        assertEquals("200", result.getStatusCode());
        assertEquals("OK", result.getStatusMessage());
        assertTrue(result.hasHeader("Date", "Some day"));
        assertTrue(result.hasHeader("Content-Length", "Some length"));
        assertNull(result.getBody());
    }

    @Test
    public void handleReturnsA501ResponseWhenTheServerDoesNotRecognizeTheMethod() throws Exception {
        String path = "/some_path";
        setControllerForHandleActionTest(path, "GET", () -> returnGenericResponse());
        clientRequest = new RequestBuilder().addPath(path).addMethod("BANANAS").build();

        Response result = controller.handle(clientRequest);

        assertEquals("501", result.getStatusCode());
        assertEquals("Not Implemented", result.getStatusMessage());
        assertEquals("501 Error: Method Not Implemented", result.getBody());
    }

    @Test
    public void handleReturnsA400BadRequestResponseIfARequestIsFlaggedAsInvalid() throws Exception {
        setControllerForHandleActionTest("/some_path", "GET", () -> returnGenericResponse());
        clientRequest = new RequestBuilder().flagAsInvalid().build();

        Response result = controller.handle(clientRequest);

        assertEquals("400", result.getStatusCode());
        assertEquals("Bad Request", result.getStatusMessage());
        assertEquals("400 Error: Bad Request Submitted", result.getBody());
    }

    private void setControllerForHandleActionTest(String path, String method, Callable<Response> action) {
        RouterBuilder builder = new RouterBuilder();
        builder.createPath(path)
                .addMethodAndAction(method, action);
        router = builder.build();
    }

    private Response returnGenericResponse() {
        return genericResponse;
    }

//    @Test
//    public void handlReturnsA200ResponseWithAListOfAvailableMethodsInResponseToAnOPTIONSRequestToASpecificPath() {
//
//    }


}
