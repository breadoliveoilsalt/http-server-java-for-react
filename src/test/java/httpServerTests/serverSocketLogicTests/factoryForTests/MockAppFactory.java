package httpServerTests.serverSocketLogicTests.factoryForTests;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerListeningLoop;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.ServerSokket;
import httpServer.serverSocketLogic.wrappers.Sokket;
import httpServerTests.serverSocketLogicTests.testableObjects.TestableThread;

public class MockAppFactory implements AppFactory {

    private ServerSokket serverSokket;
    public MockAppFactory setServerSokketToReturn(ServerSokket serverSokket) {
        this.serverSokket = serverSokket;
        return this;
    }
    public int callCountForCreateServerSokket = 0;

    private HTTPServerListeningLoop HTTPServerListeningLoop;
    public MockAppFactory setHTTPServerListeningLoopToReturn(HTTPServerListeningLoop HTTPServerListeningLoop) {
        this.HTTPServerListeningLoop = HTTPServerListeningLoop;
        return this;
    }
    public int callCountForCreateHTTPServerListeningLoop = 0;

    private Runnable clientHandlerRunnable;
    public MockAppFactory setClientHandlerRunnableToReturn(Runnable clientInitRunnable) {
        this.clientHandlerRunnable = clientInitRunnable;
        return this;
    }
    public int callCountForCreateClientHandlerRunnable = 0;

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
    public Thread createThreadFor(Runnable runnable) {
        callCountForCreateThreadFor += 1;
        return testableThreadToReturn.establishWithRunnable(runnable);
    }

    @Override
    public HTTPServerListeningLoop createHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory, ServerLogger logger) {
        callCountForCreateHTTPServerListeningLoop += 1;
        return HTTPServerListeningLoop;
    }

    @Override
    public Runnable createClientHandlerRunnable(Sokket sokket, ServerLogger logger) {
        callCountForCreateClientHandlerRunnable += 1;
        return clientHandlerRunnable;
    }

}
