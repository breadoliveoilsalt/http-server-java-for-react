package httpServerTests.serverSocketLogicTests.mocks;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerListeningLoop;
import httpServer.serverSocketLogic.wrappers.ServerSokket;

public class MockHTTPServerListeningLoop extends HTTPServerListeningLoop {

    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory) {
        super(serverSokket, factory);
    }

    @Override
    public void run() {
        callCountForRun += 1;
    }
}
