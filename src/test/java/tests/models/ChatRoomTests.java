package tests.models;

import chatServer.models.Client;
import factoryForTests.MockAppFactory;
import mocks.MockClient;
import mocks.MockListenForClientMessageRunnable;
import testableObjects.TestableChatRoom;
import testableObjects.TestableThread;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ChatRoomTests {

    private TestableChatRoom chatRoom;

    private MockAppFactory factory;
    private MockClient client1;
    private MockClient client2;
    private MockClient client3;
    private MockClient newClient;
    private MockListenForClientMessageRunnable listeningRunnable;
    private TestableThread testableThread;


    @Before
    public void initTests() {
        newClient = new MockClient();
        listeningRunnable = new MockListenForClientMessageRunnable(newClient, chatRoom);
        testableThread = new TestableThread();

        factory = new MockAppFactory();
        factory.setListenForClientMessageRunnableToReturn(listeningRunnable).setTestableThreadToReturn(testableThread);
        chatRoom = new TestableChatRoom(factory);
        addBaseClientsToChatRoom();
    }

    private void addBaseClientsToChatRoom() {
        client1 = new MockClient();
        client2 = new MockClient();
        client3 = new MockClient();
        chatRoom.setClients(new ArrayList<>(Arrays.asList(client1, client2, client3)));
    }

    @Test
    public void testBroadcastToAllClientsSendsAMessageWithTheSendingClientNameToAllClientsExceptTheSendingClient() {
        client1.clientName = "Tom";
        ArrayList<String> expectedMessages = new ArrayList<>(Arrays.asList(">> Tom: Hi.", ">> Tom: How are you?"));

        chatRoom.broadcastToAllClients(client1, "Hi.");
        chatRoom.broadcastToAllClients(client1, "How are you?");

        assertEquals(expectedMessages, client2.messagesSentToClient);
        assertEquals(expectedMessages, client3.messagesSentToClient);
    }

    @Test
    public void testBroadcastToAllClientsSendsAMessageWithAYouPromptToTheSendingClient() {
        client1.clientName = "Tom";
        ArrayList<String> exepectedMessages = new ArrayList<>(Arrays.asList(">> You: Hi.", ">> You: How are you?"));

        chatRoom.broadcastToAllClients(client1, "Hi.");
        chatRoom.broadcastToAllClients(client1, "How are you?");

        assertEquals(exepectedMessages, client1.messagesSentToClient);
    }


    @Test
    public void testAddClientAddsAClientToTheListOfClientsInTheChatRoom() {

        chatRoom.addClient(newClient);

        ArrayList<Client> chatRoomClientList = chatRoom.getClients();

        Client lastClientAddedToChatRoomList = chatRoomClientList.get(chatRoomClientList.size() - 1);

        assertEquals(newClient, lastClientAddedToChatRoomList);
    }

    @Test
    public void testAddClientBeginsAThreadListeningForAClientMessage() {
        chatRoom.addClient(newClient);
        assertEquals(listeningRunnable, testableThread.getRunnablePassedToThread());
        assertEquals(1, testableThread.getCallCountForStart());
        assertEquals(1, listeningRunnable.getCallCountForRun());
    }

    @Test
    public void testRemoveClientRemovesAClientFromTheListOfClientsInTheChatRoom() throws IOException {
        chatRoom.addClient(newClient);
        ArrayList<Client> expectedClients = new ArrayList<>(Arrays.asList(client1, client2, client3));

        chatRoom.removeClient(newClient);

        assertEquals(expectedClients, chatRoom.getClients());
    }

    @Test
    public void testRemoveClientTellsTheClientToLeave() throws IOException {
        chatRoom.addClient(newClient);

        chatRoom.removeClient(newClient);

        assertEquals(1, newClient.callCountForLeave);
    }
}
