package unitTests.mocks;

import httpServer.factory.AppFactory;
import httpServer.wrappers.Sokket;
import httpServer.httpLogic.runnable.ClientHandlerRunnable;

public class MockClientHandlerRunnable extends ClientHandlerRunnable {


    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockClientHandlerRunnable(Sokket sokket, AppFactory factory) {
        super(sokket, factory);
    }

    public void run() {
        callCountForRun += 1;
    }

}
