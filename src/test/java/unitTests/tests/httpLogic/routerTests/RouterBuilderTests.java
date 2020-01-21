package unitTests.tests.httpLogic.routerTests;

import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterBuilderTests {

    RouterBuilder builder;

    @Before
    public void testInit() {
        builder = new RouterBuilder();
    }

    @Test
    public void createPathAddsAValidPathToTheRouterBeingBuilt() {
        String path = "/some_path";
        builder.createPath(path);

        Router router = builder.build();

        assertTrue(router.getPaths().contains(path));
    }

    @Test
    public void createPathAllowsChainingOfMethodsAndActionsToPath() {
        String path = "/some_path";
        Callable<Response> actionGET = () -> buildResponseForTests();
        Callable<Response> actionPOST = () -> buildResponseForTests();

        builder.createPath(path)
            .addMethodAndAction("GET", actionGET)
            .addMethodAndAction("POST", actionPOST);

        Router router = builder.build();

        assertTrue(router.getMethodsFor(path).contains("GET"));
        assertEquals(router.getActionFor(path, "GET"), actionGET);

        assertTrue(router.getMethodsFor(path).contains("POST"));
        assertEquals(router.getActionFor(path, "POST"), actionPOST);
    }

    private Response buildResponseForTests() {
        return new Response("HTTP/1.1", "200", "OK", null, null);
    }
}
