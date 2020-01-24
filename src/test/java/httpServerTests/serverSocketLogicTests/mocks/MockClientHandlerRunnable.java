package httpServerTests.serverSocketLogicTests.mocks;

import httpServer.serverSocketLogic.wrappers.Sokket;
import httpServer.httpLogic.ClientHandlerRunnable;

public class MockClientHandlerRunnable extends ClientHandlerRunnable {


    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockClientHandlerRunnable(Sokket sokket) {
        super(sokket);
    }

    public void run() {
        callCountForRun += 1;
    }

}
