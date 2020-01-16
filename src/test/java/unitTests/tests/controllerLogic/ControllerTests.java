package unitTests.tests.controllerLogic;

import httpServer.httpLogic.controllerLogic.Controller;
import httpServer.httpLogic.controllerLogic.ControllerBuilder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
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
    private Response testResponse;;

    @Before
    public void testInit() {
        testResponse = new Response("HTTP/1.1", "200", "OK", null, null);
    }

    @Test
    public void handleCausesAnActionSpecifiedByTheControllerToCreateAResponse() throws Exception {
        String path = "/some_path";
        String method = "GET";
        Callable<Response> buildAction = () -> returnTestResponse();
        setControllerAction(path, method, buildAction);
        setClientRequest(path, method);

        Response result = controller.handle(clientRequest);

        assertEquals(testResponse, result);
    }


    private Response returnTestResponse() {
        return testResponse;
    }

    @Test
    public void getPathsReturnsASetListingValidPaths() {
        String path1 = "/simple_get";
        String path2 = "/simple_post";
        String path3 = "/simple_delete";
        setControllerPaths(path1, path2, path3);

        String[] listOfExpectedPaths = {path1, path2, path3};
        Set<String> expectedPaths = new HashSet<>(Arrays.asList(listOfExpectedPaths));

        assertEquals(expectedPaths, controller.getPaths());
    }

    private void setControllerAction(String path, String method, Callable<Response> action) {
        ControllerBuilder builder = new ControllerBuilder();
        builder.createPath(path).addMethodAndAction(method, action);
        controller = builder.buildController();
    }

    private void setClientRequest(String path, String method) {
        clientRequest = new Request();
        clientRequest.setPath(path);
        clientRequest.setMethod(method);
    }

    private void setControllerPaths(String path1, String path2, String path3) {
        ControllerBuilder builder = new ControllerBuilder();
        builder.createPath(path1);
        builder.createPath(path2);
        builder.createPath(path3);

        controller = builder.buildController();
    }
}
