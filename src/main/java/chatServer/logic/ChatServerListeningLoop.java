package chatServer.logic;

import chatServer.factory.AppFactory;
import chatServer.models.ChatRoom;
import chatServer.wrappers.ServerSokket;
import chatServer.wrappers.Sokket;

import java.io.IOException;

public class ChatServerListeningLoop implements ChatServerLogicObject {

    private final ServerSokket serverSokket;
    private final ChatRoom chatRoom;
    private final AppFactory factory;
    private Sokket connectedSokket;
    private Thread threadToStart;

    public ChatServerListeningLoop(ServerSokket serverSokket, ChatRoom chatRoom, AppFactory factory) {
        this.serverSokket = serverSokket;
        this.chatRoom = chatRoom;
        this.factory = factory;
    }

    public void run() throws IOException {
        while (serverSokket.isBoundToAPort()) {
            getSokketConnectedToClient();
            instantiateClientInitThread();
            startThread();
        }
    }

    private void getSokketConnectedToClient() throws IOException {
        connectedSokket = serverSokket.acceptConnectionAndReturnConnectedSokket();
    }

    private void instantiateClientInitThread() {
        Runnable clientInit = factory.createClientInitRunnable(connectedSokket, chatRoom, factory);
        threadToStart = factory.createThreadFor(clientInit);
    }

    private void startThread() {
        threadToStart.start();
    }

}
