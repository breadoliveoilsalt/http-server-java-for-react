package unitTests.tests.httpLogic.controllerLogicTests;

import httpServer.httpLogic.controllerLogic.Controller;
import httpServer.httpLogic.controllerLogic.ControllerBuilder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class ControllerTests {

    private Controller controller;
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
        setClientRequest(path, method);

        Response result = controller.handle(clientRequest);

        assertEquals(genericResponse, result);
    }

    @Test
    public void handleReturnsAResponseWithOnlyAPathsMetaDataInResponseToAHEADRequest() throws Exception {
        setControllerForHandleActionTest("/some_path", "GET", () -> returnGenericResponse());
        setClientRequest("/some_path", "HEAD");

        Response result = controller.handle(clientRequest);

        assertEquals("200", result.getStatusCode());
        assertEquals("OK", result.getStatusMessage());
        assertTrue(result.hasHeader("Date", "Some day"));
        assertTrue(result.hasHeader("Content-Length", "Some length"));
        assertNull(result.getBody());
    }

    @Test
    public void handleReturnsA501ResponseWhenTheServerDoesNotRecognizeTheMethod() throws Exception {
        setControllerForHandleActionTest("/some_path", "GET", () -> returnGenericResponse());
        setClientRequest("/some_path", "BANANAS");

        Response result = controller.handle(clientRequest);

        assertEquals("501", result.getStatusCode());
        assertEquals("Not Implemented", result.getStatusMessage());
        assertEquals("501 Error: Method Not Implemented", result.getBody());
    }

    @Test
    public void handleReturnsA400BadRequestResponseIfARequestIsFlaggedAsInvalid() throws Exception {
        setControllerForHandleActionTest("/some_path", "GET", () -> returnGenericResponse());
        clientRequest = new Request();
        clientRequest.flagAsInvalid();

        Response result = controller.handle(clientRequest);

        assertEquals("400", result.getStatusCode());
        assertEquals("Bad Request", result.getStatusMessage());
        assertEquals("400 Error: Bad Request Submitted", result.getBody());
    }

    private void setControllerForHandleActionTest(String path, String method, Callable<Response> action) {
        ControllerBuilder builder = new ControllerBuilder();
        builder.createPath(path)
                .addMethodAndAction(method, action);
        controller = builder.build();
    }

    private void setClientRequest(String path, String method) {
        clientRequest = new Request();
        clientRequest.setPath(path);
        clientRequest.setMethod(method);
    }

    private Response returnGenericResponse() {
        return genericResponse;
    }

    @Test
    public void getPathsReturnsASetListingValidPaths() {
        String path1 = "/simple_get";
        String path2 = "/simple_post";
        String path3 = "/simple_delete";
        setControllerForGetPathsTest(path1, path2, path3);

        String[] listOfExpectedPaths = {path1, path2, path3};
        Set<String> expectedPaths = new HashSet<>(Arrays.asList(listOfExpectedPaths));

        assertEquals(expectedPaths, controller.getPaths());
    }

    private void setControllerForGetPathsTest(String path1, String path2, String path3) {
        ControllerBuilder builder = new ControllerBuilder();
        builder.createPath(path1);
        builder.createPath(path2);
        builder.createPath(path3);

        controller = builder.build();
    }

    @Test
    public void getMethodsForReturnsASetListingValidMethodsForAPath() {
        String path = "/simple_get";
        String method1 = "GET";
        String method2 = "POST";
        setControllerForGetMethodsForTest(path, method1, method2);

        String[] listOfExpectedMethods = {method1, method2};
        Set<String> expectedMethods = new HashSet<>(Arrays.asList(listOfExpectedMethods));

        assertEquals(expectedMethods, controller.getMethodsFor(path));
    }

    private void setControllerForGetMethodsForTest(String path, String method1, String method2) {
        Callable<Response> randomBuildAction = () -> returnGenericResponse();
        ControllerBuilder builder = new ControllerBuilder();
        builder.createPath(path)
                .addMethodAndAction(method1, randomBuildAction)
                .addMethodAndAction(method2, randomBuildAction);

        controller = builder.build();
    }

    @Test
    public void getActionForReturnsTheActionAssociatedWithAPathAndMethod() {
        String path = "/some_path";
        String method = "GET";
        Callable<Response> buildAction = () -> returnGenericResponse();
        setControllerForGetActionForTest(path, method, buildAction);

        assertEquals(buildAction, controller.getActionFor(path, method));
    }

    private void setControllerForGetActionForTest(String path, String method, Callable<Response> buildAction) {
        setControllerForHandleActionTest(path, method, buildAction);
    }
}
