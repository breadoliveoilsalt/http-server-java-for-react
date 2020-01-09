package mocks;

import chatServer.factory.AppFactory;
import chatServer.models.ChatRoom;
import chatServer.models.Client;

import java.util.ArrayList;

public class MockChatRoom extends ChatRoom {

    private ArrayList<Client> clients = new ArrayList<>();

    public ArrayList<String> messagesSentToAllClients = new ArrayList<>();

    public MockChatRoom(AppFactory factory) {
        super(factory);
    }

    @Override
    public void broadcastToAllClients(Client client, String message) {
        messagesSentToAllClients.add(message);
    }

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    public Client getLastClientAdded() {
        return clients.get(clients.size() - 1);
    }

}
