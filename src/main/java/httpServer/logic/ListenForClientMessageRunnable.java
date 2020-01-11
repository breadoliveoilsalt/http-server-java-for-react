package httpServer.logic;

import httpServer.models.*;

import java.io.IOException;

public class ListenForClientMessageRunnable implements Runnable, ChatServerLogicObject {

    private final Client client;
    private final ChatRoom chatRoom;
    private String messageFromClient;

    public ListenForClientMessageRunnable(Client client, ChatRoom chatRoom) {
        this.client = client;
        this.chatRoom = chatRoom;
    }

    public void run() {
        try {
            messageFromClient = client.getMessage();
            while (!messageFromClient.equals("exit!")) {
                chatRoom.broadcastToAllClients(client, messageFromClient);
                messageFromClient = client.getMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                chatRoom.removeClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
