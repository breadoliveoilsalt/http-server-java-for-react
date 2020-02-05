package httpServerTests.httpLogicTests.requestsTests;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.Whitespace;
import org.junit.Before;
import org.junit.Test;

import httpServerTests.serverSocketLogicTests.mocks.MockSokket;
import httpServer.httpLogic.requests.RequestReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class RequestReaderTests {

    private MockSokket sokket;
    private RequestReader requestReader;

    @Before
    public void testInit() throws IOException {
        this.sokket = new MockSokket();
        this.requestReader = new RequestReader(sokket);
    }

    @Test
    public void readInputStreamReturnsAStringFromReadingTheSokketsInputStream() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1" + Whitespace.CRLF + Whitespace.CRLF;
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = requestReader.readInputStream();

        assertEquals(requestSentFromClient, requestReadFromClient);
    }

//    @Test
//    public void readInputStreamRemovesAnyTrailingCRLFs() throws IOException {
//        String requestSentFromClient = "GET /simple_get HTTP/1.1" + Whitespace.CRLF + Whitespace.CRLF;
//        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
//        sokket.setInputStream(inputStreamFromClient);
//
//        String requestReadFromClient = requestReader.readInputStream();
//
//        assertEquals("GET /simple_get HTTP/1.1", requestReadFromClient);
//    }

    @Test
    public void readInputStreamDoesNotRemoveIntermediaryCRLF() throws IOException {
        String requestBody = "Body of request";
        String requestSentFromClient =
                "GET /simple_get HTTP/1.1" + Whitespace.CRLF +
                HTTPHeaders.ContentLength + ": " + Integer.toString(requestBody.getBytes().length) + Whitespace.CRLF +
                Whitespace.CRLF +
                "Body of request";
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = requestReader.readInputStream();

        assertEquals(requestSentFromClient, requestReadFromClient);
    }

}
