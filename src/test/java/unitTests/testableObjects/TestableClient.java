package unitTests.testableObjects;

import httpServer.factory.AppFactory;
import httpServer.wrappers.Reader;
import httpServer.wrappers.Sokket;
import httpServer.wrappers.Writer;
import httpServer.models.Client;

import java.io.IOException;

public class TestableClient extends Client {

    public TestableClient(Sokket sokket, AppFactory factory) throws IOException {
        super(sokket, factory);
    }

    public String getName() {
        return clientName;
    }

    public Writer getWriter() {
        return writerToClient;
    }

    public Reader getReader() {
        return readerFromClient;
    }
}
