package httpServerTests.serverSocketLogicTests.loggerTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerLoggerTests {

    private OutputStream outputStream;
    private ServerLogger logger;

    @Before
    public void testInit() {
        outputStream = new ByteArrayOutputStream();
        logger = new ServerLogger(outputStream);
    }

    @Test
    public void printSendsAStringToTheSpecifiedOutputStream() {
        String message = "Message 1";
        assertTrue(logger.getLogList().isEmpty());
        logger.print(message);

        assertEquals(message, outputStream.toString());

        assertTrue(logger.getLogList().contains("Message 1"));
    }

    @Test
    public void printCanBeChained() throws IOException {
        String message1 = "Message 1";
        String message2 = "Message 2";
        assertTrue(logger.getLogList().isEmpty());
        logger.print(message1).print(message2);

        assertEquals(message1 + message2, outputStream.toString());
        assertTrue(logger.getLogList().contains("Message 1"));
        assertTrue(logger.getLogList().contains("Message 2"));
    }

    @Test
    public void logServerInitLogsAMessageThatTheServerStarted() {
        logger.logServerInit(5000);

        String expectedLog = "The server is now listening on port 5000";
        assertTrue(logger.getLogList().contains(expectedLog));
    }

    @Test
    public void logServerShuttingDownLogsAMessageThatTheServerIsShuttingDown() {
        logger.logServerShuttingDown();

        String expectedLog = "The server is shutting down";
        assertTrue(logger.getLogList().contains(expectedLog));
    }

    @Test
    public void logClientConnectionLogsTheConnectedHostAndItsMethodAndResourceRequested() {
        Request clientRequest = new RequestBuilder().addMethod(HTTPMethods.GET).addPath("/simple_get").addHeader("Host", "000.000.000").build();

        logger.logClientConnection(clientRequest);

        String expectedLog = "000.000.000 made a GET request to /simple_get";
        assertTrue(logger.getLogList().contains(expectedLog));
    }

}
