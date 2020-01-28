package httpServerTests.serverSocketLogicTests.mocks;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerListeningLoop;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.ServerSokket;

public class MockHTTPServerListeningLoop extends HTTPServerListeningLoop {

    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory, ServerLogger logger) {
        super(serverSokket, factory, logger);
    }

    @Override
    public void run() {
        callCountForRun += 1;
    }
}
