package httpServerTests.serverSocketLogicTests;

import httpServer.httpLogic.constants.Whitespace;
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
import java.io.OutputStream;

public class HTTPServerInitTests {

    private MockHTTPServerListeningLoop serverListeningLoop;
    private MockServerSokket serverSokket;
    private ServerLogger logger;
    private MockAppFactory factory;
    private int samplePort;
    private HTTPServerInit httpServerInit;

    @Before
    public void testInit() {
        serverSokket = new MockServerSokket();
        OutputStream loggerOutputStream = new ByteArrayOutputStream();
        logger = new ServerLogger(loggerOutputStream);
        serverListeningLoop = new MockHTTPServerListeningLoop(serverSokket, factory, logger);
        factory = new MockAppFactory()
            .setServerSokketToReturn(serverSokket)
            .setHTTPServerListeningLoopToReturn(serverListeningLoop);
        samplePort = 5000;
        httpServerInit = new HTTPServerInit(samplePort, factory, logger);
    }

    @Test
    public void runInstantiatesAListeningServerSokket() throws IOException {
        assertEquals(0, factory.callCountForCreateServerSokket);

        httpServerInit.run();

        assertEquals(1, factory.callCountForCreateServerSokket);
    }

    @Test
    public void runInstantiatesAHTTPServerListeningLoop() throws IOException {
        assertEquals(0, factory.callCountForCreateHTTPServerListeningLoop);

        httpServerInit.run();

        assertEquals(1, factory.callCountForCreateHTTPServerListeningLoop);

    }
    @Test
    public void runRunsTheHTTPServerListeningLoop() throws IOException {
        assertEquals(0, serverListeningLoop.getCallCountForRun());

        httpServerInit.run();

        assertEquals(1, serverListeningLoop.getCallCountForRun());
    }

    @Test
    public void runClosesTheServerSokketAfterTheServerListeningLoopHasBeenRun() throws IOException {
        httpServerInit.run();

        assertEquals(1, serverListeningLoop.getCallCountForRun());
        assertTrue(serverSokket.isClosed());
    }

    @Test
    public void startLogsThatTheServerStarted() throws IOException {
        assertTrue(logger.getLogList().isEmpty());
        httpServerInit.run();

        String expectedLog = "The server is now listening on port " + samplePort + "." + Whitespace.CRLF;

        assertTrue(logger.getLogList().contains(expectedLog));
    }

    @Test
    public void startLogsWhenTheServerShutsDown() throws IOException {
        assertTrue(logger.getLogList().isEmpty());
        httpServerInit.run();

        String expectedLog = "The server is shutting down." + Whitespace.CRLF;

        assertTrue(logger.getLogList().contains(expectedLog));
    }
}
