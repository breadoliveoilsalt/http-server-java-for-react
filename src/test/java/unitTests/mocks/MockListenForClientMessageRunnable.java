package unitTests.mocks;

import httpServer.models.ChatRoom;
import httpServer.models.Client;

public class MockListenForClientMessageRunnable implements Runnable {

    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockListenForClientMessageRunnable(Client client, ChatRoom chatRoom) {

    }
    public void run() {
        callCountForRun += 1;
    }
}
