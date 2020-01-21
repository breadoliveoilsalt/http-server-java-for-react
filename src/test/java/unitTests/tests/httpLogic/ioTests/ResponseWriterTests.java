package unitTests.tests.httpLogic.ioTests;

import httpServer.httpLogic.constants.Whitespace;
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

    @Before
    public void testInit() {
        this.sokket = new MockSokket();
        this.responseWriter = new ResponseWriter();
    }

    @Test
    public void writeToOutputStreamWritesAWritableResponseToTheSokketsOutputStream()   throws IOException {
        String responseToClient = "HTTP/1.1 200 OK" + Whitespace.CRLF;
        OutputStream outputStreamToClient = new ByteArrayOutputStream();
        sokket.setOutputStream(outputStreamToClient);

        responseWriter.writeToOutputStream(sokket, responseToClient);

        assertEquals(responseToClient, outputStreamToClient.toString());
    }

}
