package tests.logic;

import chatServer.logic.ClientInitRunnable;
import chatServer.models.Client;
import factoryForTests.MockAppFactory;
import mocks.MockChatRoom;
import mocks.MockClient;
import mocks.MockSokket;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClientInitRunnableTest {

    private MockSokket sokket;
    private MockChatRoom chatRoom;
    private MockAppFactory factory;
    private Client newClient;
    private ClientInitRunnable clientInitRunnable;

    @Before
    public void initTests() {
        sokket = new MockSokket();
        newClient = new MockClient();
        chatRoom = new MockChatRoom(factory);
        factory = new MockAppFactory().setClientToReturn(newClient).setChatRoomToReturn(chatRoom);
        clientInitRunnable = new ClientInitRunnable(sokket, chatRoom, factory);
    }

    @Test
    public void testRunInstantiatesANewClientFromTheAppFactory() {
        assertEquals(0, factory.callCountForCreateClient);

        clientInitRunnable.run();

        assertEquals(1, factory.callCountForCreateClient);
    }

    @Test
    public void testRunAddsTheClientToTheChatRoom() {
        clientInitRunnable.run();
        assertEquals(newClient, chatRoom.getLastClientAdded());
    }

}
