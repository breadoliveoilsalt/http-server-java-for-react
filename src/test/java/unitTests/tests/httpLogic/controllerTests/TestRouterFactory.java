package unitTests.tests.httpLogic.controllerTests;

import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;

public class TestRouterFactory {

    public static Router buildWithPathOneAndPathTwoControllers() {
        return new RouterBuilder()
                .addPathAndController("/path_one", PathOneTestController.class)
                .addPathAndController("/path_two", PathTwoTestController.class)
                .build();
    }
}
