package unitTests.mocks;

import httpServer.wrappers.Sokket;

import java.io.InputStream;
import java.io.OutputStream;

public class MockSokket implements Sokket {

    private boolean closed = false;
    public boolean isClosed() {
        return closed;
    }

    private InputStream inputStream;

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return new MockOutputStream();
    }

    @Override
    public void close() {
        closed = true;
    }

}
