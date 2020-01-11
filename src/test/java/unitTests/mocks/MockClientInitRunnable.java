package unitTests.mocks;

import httpServer.factory.AppFactory;
import httpServer.wrappers.Sokket;
import httpServer.logic.ClientInitRunnable;

public class MockClientInitRunnable extends ClientInitRunnable {


    private int callCountForRun = 0;
    public int getCallCountForRun() {
        return callCountForRun;
    }

    public MockClientInitRunnable(Sokket sokket, AppFactory factory) {
        super(sokket, factory);
    }

    public void run() {
        callCountForRun += 1;
    }

}
