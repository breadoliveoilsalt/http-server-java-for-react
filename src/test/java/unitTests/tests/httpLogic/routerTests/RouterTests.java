package unitTests.tests.httpLogic.routerTests;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RouterTests {

    class Path1Controller {
        public Response get() {
            return new ResponseBuilder().build();
        }

        public void publicFunctionThatRouterWillNotConsideredAsARecognizedMethod() {
        }

    }

    class Path2Controller {
        public Response delete() {
            return new ResponseBuilder().build();
        }
        public Response post() {
            return new ResponseBuilder().build();
        }
    }

    private Router router;

    @Before
    public void testInit() {
       router = new RouterBuilder()
               .addPathAndController("/path1", Path1Controller.class)
               .addPathAndController("/path2", Path2Controller.class)
               .build();
    }

    @Test
    public void getAllRecognizedMethodsReturnsASetOfHTTPMethodsToWhichTheControllersWillReturnAResponseCollectively() {
        Set<String> result = router.getAllRecognizedMethods();

        Set<String> expectedMethods = new HashSet<>(Arrays.asList("GET", "POST", "DELETE"));

        assertEquals(expectedMethods, result);
        assertFalse(result.contains("RANDOMVOIDFUNCTION"));
        assertFalse(result.contains("randomVoidFunction"));
    }

}
