package httpServerTests.serverSocketLogicTests.mocks;

import httpServer.serverSocketLogic.wrappers.Sokket;

import java.io.InputStream;
import java.io.OutputStream;

public class MockSokket implements Sokket {

    private boolean closed = false;
    public boolean isClosed() {
        return closed;
    }

    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void close() {
        closed = true;
    }

}
