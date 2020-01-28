package httpServerTests.serverSocketLogicTests.mocks;

import httpServer.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.Sokket;
import httpServer.httpLogic.ClientHandlerRunnable;

public class MockClientHandlerRunnable extends ClientHandlerRunnable {


    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockClientHandlerRunnable(Sokket sokket, ServerLogger logger) {
        super(sokket, logger);
    }

    public void run() {
        callCountForRun += 1;
    }

}
