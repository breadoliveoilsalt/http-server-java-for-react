package unitTests.tests.httpLogic.ioTests;

import org.junit.Before;
import org.junit.Test;

import unitTests.mocks.MockSokket;
import httpServer.httpLogic.io.ResponseWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class ResponseWriterTests {

    private MockSokket sokket;
    private ResponseWriter responseWriter;
    private final String crlf = "\r\n";

    @Before
    public void testInit() {
        this.sokket = new MockSokket();
        this.responseWriter = new ResponseWriter();
    }

    @Test
    public void writeToOutputStreamWritesAWritableResponseToTheSokketsOutputStream()   throws IOException {
        String responseToClient = "HTTP/1.1 200 OK" + crlf;
        OutputStream outputStreamToClient = new ByteArrayOutputStream();
        sokket.setOutputStream(outputStreamToClient);

        responseWriter.writeToOutputStream(sokket, responseToClient);

        assertEquals(responseToClient, outputStreamToClient.toString());
    }

}
