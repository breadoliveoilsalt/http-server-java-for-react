package unitTests.mocks;

import chatServer.models.ChatRoom;
import chatServer.models.Client;

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
