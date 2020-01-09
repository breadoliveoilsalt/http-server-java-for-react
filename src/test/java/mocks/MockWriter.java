package mocks;

import chatServer.wrappers.Writer;

import java.util.ArrayList;
import java.util.List;

public class MockWriter implements Writer {

    private final List<String> messagesSentToClient = new ArrayList<>();

    public String getLastMessageSentToClient() {
        return messagesSentToClient.get(messagesSentToClient.size() - 1);
    }

    @Override
    public void printLine(String message) {
        messagesSentToClient.add(message);
    }

}
