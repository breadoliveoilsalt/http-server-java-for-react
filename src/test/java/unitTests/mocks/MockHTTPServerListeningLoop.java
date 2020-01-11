package unitTests.mocks;

import httpServer.factory.AppFactory;
import httpServer.logic.HTTPServerListeningLoop;
import httpServer.wrappers.ServerSokket;

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
