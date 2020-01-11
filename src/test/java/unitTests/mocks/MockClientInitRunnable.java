package unitTests.mocks;

import httpServer.factory.AppFactory;
import httpServer.wrappers.Sokket;
import httpServer.logic.ClientInitRunnable;
import httpServer.models.ChatRoom;

public class MockClientInitRunnable extends ClientInitRunnable {


    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockClientInitRunnable(Sokket sokket, ChatRoom chatRoom, AppFactory factory) {
        super(sokket, chatRoom, factory);
    }

    public void run() {
        callCountForRun += 1;
    }

}
