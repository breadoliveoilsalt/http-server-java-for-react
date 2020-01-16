package unitTests.tests.httpLogic.controllerLogicTests;

import httpServer.httpLogic.controllerLogic.Controller;
import httpServer.httpLogic.controllerLogic.ControllerBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControllerBuilderTests {

    ControllerBuilder builder;

    @Before
    public void testInit() {
        builder = new ControllerBuilder();
    }

    @Test
    public void createPathAddsAValidPathToTheControllerBeingBuilt() {
        String path = "/some_path";
        builder.createPath(path);

        Controller controller = builder.buildController();

        assertTrue(controller.getPaths().contains(path));
    }

    @Test
    public void createPathAllowsChainingOfMethodsAndActionsToPath() {
        String path = "/some_path";
        Callable<Response> actionGET = () -> buildResponseForTests();
        Callable<Response> actionPOST = () -> buildResponseForTests();

        builder.createPath(path)
            .addMethodAndAction("GET", actionGET)
            .addMethodAndAction("POST", actionPOST);

        Controller controller = builder.buildController();

        assertTrue(controller.getMethodsFor(path).contains("GET"));
        assertEquals(controller.getActionFor(path, "GET"), actionGET);

        assertTrue(controller.getMethodsFor(path).contains("POST"));
        assertEquals(controller.getActionFor(path, "POST"), actionPOST);
    }

    private Response buildResponseForTests() {
        return new Response("HTTP/1.1", "200", "OK", null, null);
    }
}
