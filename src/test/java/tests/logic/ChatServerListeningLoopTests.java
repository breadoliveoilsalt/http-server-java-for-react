package tests.logic;

import chatServer.logic.ChatServerListeningLoop;
import factoryForTests.MockAppFactory;
import mocks.*;

import testableObjects.TestableThread;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatServerListeningLoopTests {

    private MockServerSokket serverSokket;
    private MockAppFactory factory;
    private MockSokket sokket;
    private MockChatRoom chatRoom;
    private MockClientInitRunnable clientInitRunnable;
    private TestableThread thread;
    private ChatServerListeningLoop chatServerListeningLoop;

    @Before
    public void testInit() {
        initServerSokket();
        initFactory();
        initChatServerListeningLoop();
        setLoopToRunOnce();
    }

    private void initServerSokket() {
        sokket = new MockSokket();
        serverSokket = new MockServerSokket();
        serverSokket.setMockSokketToReturnFollowingConnection(sokket);
    }

    private void initFactory() {
        chatRoom = new MockChatRoom(factory);
        clientInitRunnable = new MockClientInitRunnable(sokket, chatRoom, factory);
        thread = new TestableThread();
        factory = new MockAppFactory()
            .setTestableThreadToReturn(thread)
            .setClientInitRunnableToReturn(clientInitRunnable);
    }

    private void initChatServerListeningLoop() {
        chatServerListeningLoop = new ChatServerListeningLoop(serverSokket, chatRoom, factory);
    }

    private void setLoopToRunOnce() {
        ArrayList<Boolean> loopConditionWhetherServerSokketIsBound = new ArrayList<>(Arrays.asList(true, false));
        serverSokket.setIsBoundToPort(loopConditionWhetherServerSokketIsBound);
    }

    @Test
    public void testRunLoopGetsASokketConnectedToClient() throws IOException {
        setLoopToRunOnce();

        chatServerListeningLoop.run();

        assertEquals(1, serverSokket.getCallCountForAcceptConnectionAndReturnConnectedSokket());
    }

    @ Test
    public void testRunLoopInstantiatesAClientInitThread() throws IOException {
        setLoopToRunOnce();

        chatServerListeningLoop.run();

        assertEquals(1, factory.callCountForCreateClientInitRunnable);
        assertEquals(1, factory.callCountForCreateThreadFor);
    }

    @Test
    public void testRunLoopStartsTheThread() throws IOException {
        setLoopToRunOnce();

        chatServerListeningLoop.run();

        assertEquals(1, clientInitRunnable.getCallCountForRun());
        assertEquals(clientInitRunnable, thread.getRunnablePassedToThread());
        assertEquals(1, thread.getCallCountForStart());
        assertEquals(1, clientInitRunnable.getCallCountForRun());
    }

    @Test
    public void testRunLoopRepeatsSoLongAsServerSokketIsBound() throws IOException {
        setLoopToRunThreeTimes();

        chatServerListeningLoop.run();

        assertEquals(3, serverSokket.getCallCountForAcceptConnectionAndReturnConnectedSokket());
        assertEquals(3, factory.callCountForCreateClientInitRunnable);
        assertEquals(3, clientInitRunnable.getCallCountForRun());
        assertEquals(3, thread.getCallCountForStart());
        assertEquals(3, clientInitRunnable.getCallCountForRun());

    }

    private void setLoopToRunThreeTimes() {
        ArrayList<Boolean> loopConditionWhetherServerSokketIsBound = new ArrayList<>(Arrays.asList(true, true, true, false));
        serverSokket.setIsBoundToPort(loopConditionWhetherServerSokketIsBound);
    }
}
