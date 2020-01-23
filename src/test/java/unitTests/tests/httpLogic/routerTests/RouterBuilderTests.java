package unitTests.tests.httpLogic.routerTests;

import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RouterBuilderTests {

    RouterBuilder builder;

    @Before
    public void testInit() {
        builder = new RouterBuilder();
    }

    @Test
    public void addPathAndControllerMapsAPathToAControllerClass()  {
        String path = "/some_path";
        class somePathController { }
        builder.addPathAndController(path, somePathController.class);

        Router router = builder.build();

        assertEquals(somePathController.class, router.getControllerFor(path));
    }

    @Test
    public void addPathAndControllerCanBeChainedBeforeCallingBuild() {
        String path1 = "/path1";
        class path1Controller { }
        String path2 = "/path2";
        class path2Controller { }
        builder.addPathAndController(path1, path1Controller.class);
        builder.addPathAndController(path2, path2Controller.class);

        Router router = builder.build();

        assertEquals(path1Controller.class, router.getControllerFor(path1));
        assertEquals(path2Controller.class, router.getControllerFor(path2));
    }

}
