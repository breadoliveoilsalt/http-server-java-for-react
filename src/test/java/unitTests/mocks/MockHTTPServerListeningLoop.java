package unitTests.mocks;

import httpServer.factory.AppFactory;
import httpServer.logic.HTTPServerListeningLoop;
import httpServer.wrappers.ServerSokket;
import httpServer.models.ChatRoom;

public class MockHTTPServerListeningLoop extends HTTPServerListeningLoop {

    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockHTTPServerListeningLoop(ServerSokket serverSokket, ChatRoom chatRoom, AppFactory factory) {
        super(serverSokket, chatRoom, factory);
    }

    @Override
    public void run() {
        callCountForRun += 1;
    }
}
