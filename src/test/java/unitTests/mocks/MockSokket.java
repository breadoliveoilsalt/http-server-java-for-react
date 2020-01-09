package unitTests.mocks;

import chatServer.wrappers.Sokket;

import java.io.InputStream;
import java.io.OutputStream;

public class MockSokket implements Sokket {

    private boolean closed = false;
    public boolean isClosed() {
        return closed;
    }

    @Override
    public InputStream getInputStream() {
        return new MockInputStream();
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
