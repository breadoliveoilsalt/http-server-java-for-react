package unitTests.factoryForTests;

import httpServer.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerListeningLoop;
import httpServer.wrappers.Reader;
import httpServer.wrappers.ServerSokket;
import httpServer.wrappers.Sokket;
import httpServer.wrappers.Writer;
import unitTests.testableObjects.TestableThread;

import java.io.InputStream;
import java.io.OutputStream;

public class MockAppFactory implements AppFactory {

    private ServerSokket serverSokket;
    public MockAppFactory setServerSokketToReturn(ServerSokket serverSokket) {
        this.serverSokket = serverSokket;
        return this;
    }
    public int callCountForCreateServerSokket = 0;

    private Reader reader;
    public MockAppFactory setReaderToReturn(Reader reader) {
        this.reader = reader;
        return this;
    }

    private Writer writer;
    public MockAppFactory setWriterToReturn(Writer writer) {
        this.writer = writer;
        return this;
    }

    private HTTPServerListeningLoop HTTPServerListeningLoop;
    public MockAppFactory setHTTPServerListeningLoopToReturn(HTTPServerListeningLoop HTTPServerListeningLoop) {
        this.HTTPServerListeningLoop = HTTPServerListeningLoop;
        return this;
    }
    public int callCountForCreateHTTPServerListeningLoop = 0;

    private Runnable clientInitRunnable;
    public MockAppFactory setClientInitRunnableToReturn(Runnable clientInitRunnable) {
        this.clientInitRunnable = clientInitRunnable;
        return this;
    }
    public int callCountForCreateClientInitRunnable = 0;


    private Runnable listenForClientMessageRunnableToReturn;
    public MockAppFactory setListenForClientMessageRunnableToReturn(Runnable listenForClientMessageRunnableToReturn) {
        this.listenForClientMessageRunnableToReturn = listenForClientMessageRunnableToReturn;
        return this;
    }

    public int callCountForCreateThreadFor = 0;

    private TestableThread testableThreadToReturn;
    public MockAppFactory setTestableThreadToReturn(TestableThread testableThreadToReturn) {
        this.testableThreadToReturn = testableThreadToReturn;
        return this;
    }

    @Override
    public ServerSokket createServerSokketListeningAtPort(int port) {
        callCountForCreateServerSokket += 1;
        return serverSokket;
    }

    @Override
    public Reader createReader(InputStream inputStream) {
        return reader;
    }

    @Override
    public Writer createWriter(OutputStream outputStream) {
        return writer;
    }

    @Override
    public Thread createThreadFor(Runnable runnable) {
        callCountForCreateThreadFor += 1;
        return testableThreadToReturn.establishWithRunnable(runnable);
    }

    @Override
    public HTTPServerListeningLoop createHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory) {
        callCountForCreateHTTPServerListeningLoop += 1;
        return HTTPServerListeningLoop;
    }

    @Override
    public Runnable createClientInitRunnable(Sokket sokket, AppFactory factory) {
        callCountForCreateClientInitRunnable += 1;
        return clientInitRunnable;
    }

}
