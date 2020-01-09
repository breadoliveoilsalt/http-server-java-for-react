package testableObjects;

import chatServer.factory.AppFactory;
import chatServer.models.ChatRoom;
import chatServer.models.Client;

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
