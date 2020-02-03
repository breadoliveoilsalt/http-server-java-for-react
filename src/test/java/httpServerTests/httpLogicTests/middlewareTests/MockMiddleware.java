package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.middleware.Middleware;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class MockMiddleware extends Middleware {

    public boolean handleWasCalled = false;

    @Override
    public void handle(Request request, Response response) {
        handleWasCalled = true;
    }

}
