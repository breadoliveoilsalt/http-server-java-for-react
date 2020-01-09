package unitTests.mocks;

import chatServer.models.Client;

import java.util.ArrayList;

public class MockClient extends Client {

    public int callCountForLeave = 0;
    public ArrayList<String> messagesSentToClient = new ArrayList<>();
    public ArrayList<String> messagesFromClient = new ArrayList<>();
    public String clientName;

    @Override
    public void leave() {
        callCountForLeave += 1;
    }

    @Override
    public void sendMessage(String string) {
        messagesSentToClient.add(string);
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public String getMessage() {
        return messagesFromClient.remove(0);
    }

}
