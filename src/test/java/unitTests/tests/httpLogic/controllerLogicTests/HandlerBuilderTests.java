package unitTests.tests.httpLogic.controllerLogicTests;

import httpServer.httpLogic.router.Handler;
import httpServer.httpLogic.router.ControllerBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandlerBuilderTests {

    ControllerBuilder builder;

    @Before
    public void testInit() {
        builder = new ControllerBuilder();
    }

    @Test
    public void createPathAddsAValidPathToTheControllerBeingBuilt() {
        String path = "/some_path";
        builder.createPath(path);

        Handler handler = builder.build();

        assertTrue(handler.getPaths().contains(path));
    }

    @Test
    public void createPathAllowsChainingOfMethodsAndActionsToPath() {
        String path = "/some_path";
        Callable<Response> actionGET = () -> buildResponseForTests();
        Callable<Response> actionPOST = () -> buildResponseForTests();

        builder.createPath(path)
            .addMethodAndAction("GET", actionGET)
            .addMethodAndAction("POST", actionPOST);

        Handler handler = builder.build();

        assertTrue(handler.getMethodsFor(path).contains("GET"));
        assertEquals(handler.getActionFor(path, "GET"), actionGET);

        assertTrue(handler.getMethodsFor(path).contains("POST"));
        assertEquals(handler.getActionFor(path, "POST"), actionPOST);
    }

    private Response buildResponseForTests() {
        return new Response("HTTP/1.1", "200", "OK", null, null);
    }
}
