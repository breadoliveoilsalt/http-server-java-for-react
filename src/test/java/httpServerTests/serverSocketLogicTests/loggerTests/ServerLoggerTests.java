package httpServerTests.serverSocketLogicTests.loggerTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

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
        assertTrue(logger.getLogList().isEmpty());
        logger.print("Message 1");

        assertTrue(logger.getLogList().contains("Message 1"));
    }

    @Test
    public void printCanBeChained() {
        assertTrue(logger.getLogList().isEmpty());
        logger.print("Message 1").print("Message 2");

        assertTrue(logger.getLogList().contains("Message 1"));
        assertTrue(logger.getLogList().contains("Message 2"));
    }

    @Test
    public void logServerInitLogsAMessageThatTheServerStarted() {
        logger.logServerInit("5000");

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
        Request clientRequest = new RequestBuilder().addMethod(HTTPMethods.GET).addPath("/simple_get").addHeader("Host", "000.000.000");

        logger.logClientConnection(clientRequest);

        String expectedLog = "000.000.000 made a GET request to /simple_get";
        assertTrue(logger.getLogList().contains(expectedLog));
    }

}
