package unitTests.tests.models;

import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.*;
import unitTests.testableObjects.TestableClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;

public class ClientTests {

    private MockSokket sokket;
    private MockAppFactory factory;
    private MockWriter writer;
    private MockReader reader;
    private TestableClient client;

    @Before
    public void testInit() throws IOException {
        sokket = new MockSokket();
        writer = new MockWriter();
        reader = new MockReader();
        reader.setMockMessagesToReceiveFromClient(new ArrayList<>(Arrays.asList("Random Name")));
        factory = new MockAppFactory()
                .setWriterToReturn(writer)
                .setReaderToReturn(reader);
        client = new TestableClient(sokket, factory);
    }

    @Test
    public void testNewClientWithArgumentsInstantiatesAReaderAndWriterForClient() {
        assertEquals(writer, client.getWriter());
        assertEquals(reader, client.getReader());
    }

    @Test
    public void testNewClientWithArgumentsSetsTheClientNameThroughReadingALineFromTheReader() throws IOException {
        reader.setMockMessagesToReceiveFromClient(new ArrayList<>(Arrays.asList("Tom")));
        client = new TestableClient(sokket, factory);

        assertEquals("Tom", client.getName());
    }

    @Test
    public void testSendMessagePrintsToTheWriter() {
        client.sendMessage("Hi, there.");

        assertEquals("Hi, there.", writer.getLastMessageSentToClient());
    }


    @Test
    public void testGetMessageReadsALineFromTheReader() throws IOException {
        String expectedResult = "Hello!";
        reader.setMockMessagesToReceiveFromClient(new ArrayList<>(Arrays.asList(expectedResult)));

        String actualResult = client.getMessage();

        assertSame(expectedResult, actualResult);
    }


    @Test
    public void testLeaveClosesTheSokket() throws IOException {
        assertFalse(sokket.isClosed());

        client.leave();

        assertTrue(sokket.isClosed());

    }
}
