package httpServerTests.httpLogicTests.controllerTests;

import httpServer.router.Router;
import httpServer.router.RouterBuilder;

public class TestRouterFactory {

    public static Router buildWithPathOneAndPathTwoControllers() {
        return new RouterBuilder()
                .addPathAndController("/path_one", PathOneTestController.class)
                .addPathAndController("/path_two", PathTwoTestController.class)
                .build();
    }
}
