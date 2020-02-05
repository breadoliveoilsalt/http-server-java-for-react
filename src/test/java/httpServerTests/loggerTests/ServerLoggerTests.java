package httpServerTests.loggerTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.serverLogger.ServerLogger;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
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

        String expectedMessage = message + Whitespace.CRLF;
        assertEquals(expectedMessage, outputStream.toString());

        assertTrue(logger.getLogList().contains(expectedMessage));
    }

    @Test
    public void printCanBeChained() {
        String message1 = "Message 1";
        String message2 = "Message 2";

        assertTrue(logger.getLogList().isEmpty());
        logger.print(message1).print(message2);

        String expectedLog = message1 + Whitespace.CRLF + message2 + Whitespace.CRLF;

        assertEquals(expectedLog, outputStream.toString());
        assertTrue(logger.getLogList().contains("Message 1" + Whitespace.CRLF));
        assertTrue(logger.getLogList().contains("Message 2" + Whitespace.CRLF));
    }

    @Test
    public void logServerInitLogsAMessageThatTheServerStarted() {
        logger.logServerInit(5000);

        String expectedLog = "The server is now listening on port 5000." + Whitespace.CRLF;
        assertTrue(logger.getLogList().contains(expectedLog));
    }

    @Test
    public void logServerShuttingDownLogsAMessageThatTheServerIsShuttingDown() {
        logger.logServerShuttingDown();

        String expectedLog = "The server is shutting down." + Whitespace.CRLF;
        assertTrue(logger.getLogList().contains(expectedLog));
    }

    @Test
    public void logRequestAndResponseLogsInformationAboutTheRequestAndResponse() {
        Request request  = new RequestBuilder().addMethod(HTTPMethods.GET).addPath("/simple_get").addHeader("Host", "000.000.000").build();

        Response response = new Response();
        response.statusCode = HTTPStatusCodes.OK;

        logger.logRequestAndResponse(request, response);

        String expectedLog =
                Whitespace.DIVIDER +
                Whitespace.CRLF +
                "000.000.000 made a GET request to /simple_get." +
                Whitespace.CRLF +
                "The server responded with a 200 status code." +
                Whitespace.CRLF +
                Whitespace.DIVIDER;

        assertTrue(logger.getLogList().contains(expectedLog));
    }

}
