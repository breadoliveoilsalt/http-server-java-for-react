package chatServer.models;

import chatServer.factory.AppFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ChatRoom {

    private final AppFactory factory;
    protected ArrayList<Client> clients = new ArrayList<>();

    public ChatRoom(AppFactory factory) {
        this.factory = factory;
    }

    public synchronized void broadcastToAllClients(Client sendingClient, String message) {
        for (Client client : clients) {
            if (client != sendingClient) {
                client.sendMessage(">> " + sendingClient.getClientName() + ": " + message);
            } else {
                client.sendMessage(">> You: " + message);
            }
        }
    }

    public synchronized void addClient(Client client) {
        clients.add(client);
        beginListeningThreadForClient(client);
    }

    public synchronized void removeClient(Client client) throws IOException {
        clients.remove(client);
        client.leave();
    }

    private void beginListeningThreadForClient(Client client) {
        Runnable runnable = factory.createListenForClientMessageRunnable(client, this);
        Thread thread = factory.createThreadFor(runnable);
        thread.start();
    }

}
