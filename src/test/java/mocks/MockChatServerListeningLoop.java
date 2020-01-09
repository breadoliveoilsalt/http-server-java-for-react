package mocks;

import chatServer.factory.AppFactory;
import chatServer.wrappers.ServerSokket;
import chatServer.logic.ChatServerListeningLoop;
import chatServer.models.ChatRoom;

public class MockChatServerListeningLoop extends ChatServerListeningLoop {

    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockChatServerListeningLoop(ServerSokket serverSokket, ChatRoom chatRoom, AppFactory factory) {
        super(serverSokket, chatRoom, factory);
    }

    @Override
    public void run() {
        callCountForRun += 1;
    }
}
