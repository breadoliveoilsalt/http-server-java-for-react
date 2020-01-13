package unitTests.tests.serverSocketLogic;

import httpServer.serverSocketLogic.HTTPServerListeningLoop;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.*;

import unitTests.testableObjects.TestableThread;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HTTPServerListeningLoopTests {

    private MockServerSokket serverSokket;
    private MockAppFactory factory;
    private MockSokket sokket;
    private MockClientHandlerRunnable clientInitRunnable;
    private TestableThread thread;
    private HTTPServerListeningLoop HTTPServerListeningLoop;

    @Before
    public void testInit() {
        initServerSokket();
        initFactory();
        initHTTPServerListeningLoop();
        setLoopToRunOnce();
    }

    private void initServerSokket() {
        sokket = new MockSokket();
        serverSokket = new MockServerSokket();
        serverSokket.setMockSokketToReturnFollowingConnection(sokket);
    }

    private void initFactory() {
        clientInitRunnable = new MockClientHandlerRunnable(sokket, factory);
        thread = new TestableThread();
        factory = new MockAppFactory()
            .setTestableThreadToReturn(thread)
            .setClientInitRunnableToReturn(clientInitRunnable);
    }

    private void initHTTPServerListeningLoop() {
        HTTPServerListeningLoop = new HTTPServerListeningLoop(serverSokket, factory);
    }

    private void setLoopToRunOnce() {
        ArrayList<Boolean> loopConditionWhetherServerSokketIsBound = new ArrayList<>(Arrays.asList(true, false));
        serverSokket.setIsBoundToPort(loopConditionWhetherServerSokketIsBound);
    }

    @Test
    public void testRunLoopGetsASokketConnectedToClient() throws IOException {
        setLoopToRunOnce();

        HTTPServerListeningLoop.run();

        assertEquals(1, serverSokket.getCallCountForAcceptConnectionAndReturnConnectedSokket());
    }

    @ Test
    public void testRunLoopInstantiatesAClientInitThread() throws IOException {
        setLoopToRunOnce();

        HTTPServerListeningLoop.run();

        assertEquals(1, factory.callCountForCreateClientInitRunnable);
        assertEquals(1, factory.callCountForCreateThreadFor);
    }

    @Test
    public void testRunLoopStartsTheThread() throws IOException {
        setLoopToRunOnce();

        HTTPServerListeningLoop.run();

        assertEquals(1, clientInitRunnable.getCallCountForRun());
        assertEquals(clientInitRunnable, thread.getRunnablePassedToThread());
        assertEquals(1, thread.getCallCountForStart());
        assertEquals(1, clientInitRunnable.getCallCountForRun());
    }

    @Test
    public void testRunLoopRepeatsSoLongAsServerSokketIsBound() throws IOException {
        setLoopToRunThreeTimes();

        HTTPServerListeningLoop.run();

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
