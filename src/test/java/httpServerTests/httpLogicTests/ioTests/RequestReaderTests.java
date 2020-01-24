package httpServerTests.httpLogicTests.ioTests;

import httpServer.httpLogic.constants.Whitespace;
import org.junit.Before;
import org.junit.Test;

import httpServerTests.serverSocketLogicTests.mocks.MockSokket;
import httpServer.httpLogic.io.RequestReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class RequestReaderTests {

    private MockSokket sokket;
    private RequestReader requestReader;

    @Before
    public void testInit() {
        this.sokket = new MockSokket();
        this.requestReader = new RequestReader();
    }

    @Test
    public void readInputStreamReturnsAStringFromReadingTheSokketsInputStream() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1";
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = requestReader.readInputStream(sokket);

        assertEquals(requestSentFromClient, requestReadFromClient);
    }

    @Test
    public void readInputStreamRemovesAnyTrailingCRLFs() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1" + Whitespace.CRLF + Whitespace.CRLF;
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = requestReader.readInputStream(sokket);

        assertEquals("GET /simple_get HTTP/1.1", requestReadFromClient);
    }

    @Test
    public void readInputStreamDoesNotRemoveIntermediaryCRLF() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1" + Whitespace.CRLF + Whitespace.CRLF + "Body of request";
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = requestReader.readInputStream(sokket);

        assertEquals(requestSentFromClient, requestReadFromClient);
    }

}
