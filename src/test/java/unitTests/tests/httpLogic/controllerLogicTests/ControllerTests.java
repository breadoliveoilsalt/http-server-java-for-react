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

import static org.junit.Assert.assertEquals;

public class ControllerTests {

    private Controller controller;
    private Request clientRequest;
    private Response genericResponse;

    @Before
    public void testInit() {
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
        Callable<Response> buildAction = () -> returnGenericResponse();
        setControllerForHandleActionTest(path, method, buildAction);
        setClientRequest(path, method);

        Response result = controller.handle(clientRequest);

        assertEquals(genericResponse, result);
    }

    private Response returnGenericResponse() {
        return genericResponse;
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

    @Test
    public void handleReturnsAResponseWithOnlyAPathsMetaDataInResponseToAHEADRequest() throws Exception {
        String path = "/some_path";
        String method = "GET";
        Callable<Response> buildAction = () -> returnGenericResponse();
        setControllerForHandleActionTest(path, method, buildAction);
        setClientRequest(path, method);

        Response expectedResponse = new ResponseBuilder()
            .addStatusCode("200")
            .addStatusMessage("OK")
            .addHeader("Date", "Some day")
            .addHeader("Content-Length", "Some length")
            .build();

        Response result = controller.handle(clientRequest);

        assertEquals(expectedResponse, result);
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
