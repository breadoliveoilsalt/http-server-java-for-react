package httpServerTests.serverSocketLogicTests;

import httpServer.serverSocketLogic.HTTPServerInit;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;
import httpServerTests.serverSocketLogicTests.factoryForTests.MockAppFactory;
import httpServerTests.serverSocketLogicTests.mocks.MockHTTPServerListeningLoop;
import httpServerTests.serverSocketLogicTests.mocks.MockServerSokket;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HTTPServerInitTests {

    private MockHTTPServerListeningLoop serverListeningLoop;
    private MockServerSokket serverSokket;
    private MockAppFactory factory;
    private HTTPServerInit httpServerInit;

    @Before
    public void testInit() {
        serverSokket = new MockServerSokket();
        ServerLogger logger = new ServerLogger(new ByteArrayOutputStream());
        serverListeningLoop = new MockHTTPServerListeningLoop(serverSokket, factory, logger);
        factory = new MockAppFactory()
            .setServerSokketToReturn(serverSokket)
            .setHTTPServerListeningLoopToReturn(serverListeningLoop);
        int samplePort = 8000;
        httpServerInit = new HTTPServerInit(samplePort, factory, new ServerLogger(System.out));
    }

    @Test
    public void testStartInstantiatesAListeningServerSokket() throws IOException {
        assertEquals(0, factory.callCountForCreateServerSokket);

        httpServerInit.run();

        assertEquals(1, factory.callCountForCreateServerSokket);
    }

    @Test
    public void testStartInstantiatesAHTTPServerListeningLoop() throws IOException {
        assertEquals(0, factory.callCountForCreateHTTPServerListeningLoop);

        httpServerInit.run();

        assertEquals(1, factory.callCountForCreateHTTPServerListeningLoop);

    }
    @Test
    public void testStartRunsTheHTTPServerListeningLoop() throws IOException {
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
