package tests.logic;

import chatServer.logic.ChatServerInit;
import factoryForTests.MockAppFactory;
import mocks.MockChatRoom;
import mocks.MockChatServerListeningLoop;
import mocks.MockServerSokket;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class ChatServerInitTests {

    private MockChatServerListeningLoop serverListeningLoop;
    private MockServerSokket serverSokket;
    private MockChatRoom chatRoom;
    private MockAppFactory factory;
    private ChatServerInit chatServer;

    @Before
    public void testInit() {
        serverSokket = new MockServerSokket();
        chatRoom = new MockChatRoom(factory);
        serverListeningLoop = new MockChatServerListeningLoop(serverSokket, chatRoom, factory);
        factory = new MockAppFactory()
            .setServerSokketToReturn(serverSokket)
            .setChatServerListeningLoopToReturn(serverListeningLoop);
        int samplePort = 8000;
        chatServer = new ChatServerInit(samplePort, factory);
    }

    @Test
    public void testStartInstantiatesAListeningServerSokket() throws IOException {
        assertEquals(0, factory.callCountForCreateServerSokket);

        chatServer.run();

        assertEquals(1, factory.callCountForCreateServerSokket);
    }

    @Test
    public void testStartInstantiatesAChatRoom() throws IOException {
        assertEquals(0, factory.callCountForCreateChatRoom);

        chatServer.run();

        assertEquals(1, factory.callCountForCreateChatRoom);
    }

    @Test
    public void testStartInstantiatesAChatServerListeningLoop() throws IOException {
        assertEquals(0, factory.callCountForCreateChatServerListeningLoop);

        chatServer.run();

        assertEquals(1, factory.callCountForCreateChatServerListeningLoop);

    }
    @Test
    public void testStartRunsTheChatServerListeningLoop() throws IOException {
        assertEquals(0, serverListeningLoop.getCallCountForRun());

        chatServer.run();

        assertEquals(1, serverListeningLoop.getCallCountForRun());
    }

    @Test
    public void testStartClosesTheServerSokketAfterTheServerListeningLoopHasBeenRun() throws IOException {
        chatServer.run();

        assertEquals(1, serverListeningLoop.getCallCountForRun());
        assertTrue(serverSokket.isClosed());
    }

}
