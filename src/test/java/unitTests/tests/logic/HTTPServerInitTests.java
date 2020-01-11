package unitTests.tests.logic;

import httpServer.logic.HTTPServerInit;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.MockHTTPServerListeningLoop;
import unitTests.mocks.MockServerSokket;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class HTTPServerInitTests {

    private MockHTTPServerListeningLoop serverListeningLoop;
    private MockServerSokket serverSokket;
    private MockAppFactory factory;
    private HTTPServerInit httpServerInit;

    @Before
    public void testInit() {
        serverSokket = new MockServerSokket();
        serverListeningLoop = new MockHTTPServerListeningLoop(serverSokket, factory);
        factory = new MockAppFactory()
            .setServerSokketToReturn(serverSokket)
            .setChatServerListeningLoopToReturn(serverListeningLoop);
        int samplePort = 8000;
        httpServerInit = new HTTPServerInit(samplePort, factory);
    }

    @Test
    public void testStartInstantiatesAListeningServerSokket() throws IOException {
        assertEquals(0, factory.callCountForCreateServerSokket);

        httpServerInit.run();

        assertEquals(1, factory.callCountForCreateServerSokket);
    }

    @Test
    public void testStartInstantiatesAChatServerListeningLoop() throws IOException {
        assertEquals(0, factory.callCountForCreateChatServerListeningLoop);

        httpServerInit.run();

        assertEquals(1, factory.callCountForCreateChatServerListeningLoop);

    }
    @Test
    public void testStartRunsTheChatServerListeningLoop() throws IOException {
        assertEquals(0, serverListeningLoop.getCallCountForRun());

        httpServerInit.run();

        assertEquals(1, serverListeningLoop.getCallCountForRun());
    }

    @Test
    public void testStartClosesTheServerSokketAfterTheServerListeningLoopHasBeenRun() throws IOException {
        httpServerInit.run();

        assertEquals(1, serverListeningLoop.getCallCountForRun());
        assertTrue(serverSokket.isClosed());
    }

}
