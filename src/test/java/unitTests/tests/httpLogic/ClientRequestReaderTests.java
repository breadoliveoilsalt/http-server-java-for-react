package unitTests.tests.httpLogic;

import httpServer.httpLogic.ClientRequestReader;
import org.junit.Before;
import org.junit.Test;
import unitTests.mocks.MockSokket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class ClientRequestReaderTests {

    private MockSokket sokket;
    private final String crlf = "\r\n";

    @Before
    public void testInit() {
        this.sokket = new MockSokket();
    }

    @Test
    public void readInputStreamReturnsAStringFromReadingTheSokketsInputStream() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1";
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = ClientRequestReader.readInputStream(sokket);

        assertEquals(requestSentFromClient, requestReadFromClient);
    }

    @Test
    public void readInputStreamRemovesTheLastCRLF() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1" + crlf;
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = ClientRequestReader.readInputStream(sokket);

        assertEquals("GET /simple_get HTTP/1.1", requestReadFromClient);
    }

    @Test
    public void readInputStreamDoesNotRemoveIntermediaryCRLF() throws IOException {
        String requestSentFromClient = "GET /simple_get HTTP/1.1" + crlf + "Body of request";
        InputStream inputStreamFromClient = new ByteArrayInputStream(requestSentFromClient.getBytes());
        sokket.setInputStream(inputStreamFromClient);

        String requestReadFromClient = ClientRequestReader.readInputStream(sokket);

        assertEquals(requestSentFromClient, requestReadFromClient);
    }

}
