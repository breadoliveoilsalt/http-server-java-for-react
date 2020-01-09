package unitTests.tests.logic;

import chatServer.logic.ListenForClientMessageRunnable;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenForClientMessageRunnableTests {

    private MockClient client;
    private MockChatRoom chatRoom;
    private MockAppFactory factory;
    private Runnable listenForClientMessageRunnable;
    private List<String> expectedSentMessages;

    @Before
    public void init() {
        factory = new MockAppFactory();
        client = new MockClient();
        chatRoom = new MockChatRoom(factory);
        listenForClientMessageRunnable = new ListenForClientMessageRunnable(client, chatRoom);
        expectedSentMessages = new ArrayList<>();
    }

    @Test
    public void testRunBeginsALoopThatGetsMessageFromClientAndTellsChatRoomToBroadcastItToAllClients() {
        client.messagesFromClient = new ArrayList<>(Arrays.asList("Hello!", "exit!"));
        expectedSentMessages.add("Hello!");

        listenForClientMessageRunnable.run();

        assertEquals(expectedSentMessages, chatRoom.messagesSentToAllClients);
    }

     @Test public void testRunContinuesTheLoopUntilClientSendsExitMessage() {
        client.messagesFromClient = new ArrayList<>(Arrays.asList("Hello!", "How are you?", "Bye!", "exit!"));
        expectedSentMessages.addAll(Arrays.asList("Hello!", "How are you?", "Bye!"));

        listenForClientMessageRunnable.run();

        assertEquals(expectedSentMessages, chatRoom.messagesSentToAllClients);
     }

     @Test public void testRunStopsAsSoonAsClientSendsExitMessage() {
         client.messagesFromClient = new ArrayList<>(Arrays.asList("exit!", "Hello!", "How are you?"));

         listenForClientMessageRunnable.run();

         assertTrue(chatRoom.messagesSentToAllClients.isEmpty());
     }
}
