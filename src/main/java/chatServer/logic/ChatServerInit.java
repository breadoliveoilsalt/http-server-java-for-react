package chatServer.logic;

import chatServer.factory.AppFactory;
import chatServer.wrappers.ServerSokket;
import chatServer.models.ChatRoom;

import java.io.IOException;

public class ChatServerInit implements ChatServerLogicObject {

    private final int port;
    private ChatServerLogicObject chatServerListeningLoop;
    private final AppFactory factory;
    private ChatRoom chatRoom;
    private ServerSokket serverSokket;

    public ChatServerInit(int port, AppFactory factory) {
        this.port = port;
        this.factory = factory;
    }

    public void run() throws IOException {

        try {
            instantiateServerSokket();
            instantiateChatRoom();
            instantiateChatServerListeningLoop();
            runChatServerListeningLoop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServerSokket();
        }

    }

    private void instantiateServerSokket() throws IOException {
        serverSokket = factory.createServerSokketListeningAtPort(port);
    }

    private void instantiateChatRoom() {
        chatRoom = factory.createChatRoom(factory);
    }
    
    private void instantiateChatServerListeningLoop() {
        chatServerListeningLoop = factory.createChatServerListeningLoop(serverSokket, chatRoom, factory);
    }

    private void runChatServerListeningLoop() throws IOException {
        chatServerListeningLoop.run();
    }

    private void closeServerSokket() throws IOException {
        serverSokket.close();
    }

}
