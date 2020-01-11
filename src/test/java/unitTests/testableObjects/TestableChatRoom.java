package unitTests.testableObjects;

import httpServer.factory.AppFactory;
import httpServer.models.ChatRoom;
import httpServer.models.Client;

import java.util.ArrayList;

public class TestableChatRoom extends ChatRoom {

    public TestableChatRoom(AppFactory factory) {
        super(factory);
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }
}
