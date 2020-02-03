package httpServerTests.httpLogicTests.testRouterAndControllers;

import httpServer.router.Router;
import httpServer.router.RouterBuilder;

public class TestRouterFactory {

    public static Router buildWithPathOneAndPathTwoTestControllers() {
        return new RouterBuilder()
                .addPathAndController(TestPaths.pathOne, PathOneTestController.class)
                .addPathAndController(TestPaths.pathTwo, PathTwoTestController.class)
                .build();
    }
}
