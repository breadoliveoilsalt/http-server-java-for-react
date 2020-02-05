package httpServerTests.serverSocketLogicTests.factoryForTests;

import httpServer.router.Router;
import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerListeningLoop;
import httpServer.serverLogger.ServerLogger;
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

    @Override
    public ServerSokket createServerSokketListeningAtPort(int port) {
        callCountForCreateServerSokket += 1;
        return serverSokket;
    }

    private HTTPServerListeningLoop HTTPServerListeningLoop;
    public MockAppFactory setHTTPServerListeningLoopToReturn(HTTPServerListeningLoop HTTPServerListeningLoop) {
        this.HTTPServerListeningLoop = HTTPServerListeningLoop;
        return this;
    }
    public int callCountForCreateHTTPServerListeningLoop = 0;

    @Override
    public HTTPServerListeningLoop createHTTPServerListeningLoop(ServerSokket serverSokket, Router router, AppFactory factory, ServerLogger logger) {
        callCountForCreateHTTPServerListeningLoop += 1;
        return HTTPServerListeningLoop;
    }

    private Runnable clientHandlerRunnable;
    public MockAppFactory setClientHandlerRunnableToReturn(Runnable clientInitRunnable) {
        this.clientHandlerRunnable = clientInitRunnable;
        return this;
    }
    public int callCountForCreateClientHandlerRunnable = 0;

    @Override
    public Runnable createClientHandlerRunnable(Sokket sokket, Router router, ServerLogger logger) {
        callCountForCreateClientHandlerRunnable += 1;
        return clientHandlerRunnable;
    }

    public int callCountForCreateThreadFor = 0;

    private TestableThread testableThreadToReturn;
    public MockAppFactory setTestableThreadToReturn(TestableThread testableThreadToReturn) {
        this.testableThreadToReturn = testableThreadToReturn;
        return this;
    }

    @Override
    public Thread createThreadFor(Runnable runnable) {
        callCountForCreateThreadFor += 1;
        return testableThreadToReturn.establishWithRunnable(runnable);
    }

    public int callCountForCreateRouter = 0;
    private Router router;

    @Override
    public Router createRouter() {
        callCountForCreateRouter += 1;
        return router;
    }

}
