package unitTests.mocks;

import chatServer.wrappers.Reader;

import java.util.ArrayList;
import java.util.List;

public class MockReader implements Reader {

    private List<String> mockMessagesFromClient;
    public void setMockMessagesToReceiveFromClient(ArrayList<String> mockMessagesToReceiveFromClient) {
        this.mockMessagesFromClient = mockMessagesToReceiveFromClient;
    }

    @Override
    public String readLine() {
        return mockMessagesFromClient.remove(0);
    }


}
