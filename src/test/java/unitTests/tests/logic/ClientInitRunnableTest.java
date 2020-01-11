package unitTests.tests.logic;

import httpServer.logic.ClientInitRunnable;
import httpServer.models.Client;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.MockChatRoom;
import unitTests.mocks.MockClient;
import unitTests.mocks.MockSokket;

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
