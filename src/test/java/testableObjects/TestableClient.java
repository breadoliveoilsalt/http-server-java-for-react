package testableObjects;

import chatServer.factory.AppFactory;
import chatServer.wrappers.Reader;
import chatServer.wrappers.Sokket;
import chatServer.wrappers.Writer;
import chatServer.models.Client;

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
