package unitTests.tests.controllerLogic;

import httpServer.httpLogic.controllerLogic.Controller;
import httpServer.httpLogic.controllerLogic.ControllerBuilder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

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
        setController(path, method, buildAction);
        setClientRequest(path, method);

        Response result = controller.handle(clientRequest);

        assertEquals(testResponse, result);
    }

    private void setController(String path, String method, Callable<Response> action) {
        ControllerBuilder builder = new ControllerBuilder();
        builder.createPath(path).addMethodAndAction(method, action);
        controller = builder.buildController();
    }

    private void setClientRequest(String path, String method) {
        clientRequest = new Request();
        clientRequest.setPath(path);
        clientRequest.setMethod(method);

    }

    private Response returnTestResponse() {
        return testResponse;
    }
}
