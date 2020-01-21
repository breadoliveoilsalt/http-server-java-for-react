package unitTests.mocks;

import httpServer.wrappers.Sokket;
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
