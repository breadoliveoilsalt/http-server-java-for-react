package httpServerTests.serverSocketLogicTests.mocks;

import httpServer.serverSocketLogic.wrappers.ServerSokket;
import httpServer.serverSocketLogic.wrappers.Sokket;

import java.util.ArrayList;
import java.util.List;

public class MockServerSokket implements ServerSokket {

    private Sokket connectedSokket = null;
    public void setMockSokketToReturnFollowingConnection(Sokket sokket) {
        connectedSokket = sokket;
    }

    private boolean closed = false;
    public boolean isClosed() {
        return closed;
    }

    private List<Boolean> boundToAPort;
    public boolean isBoundToAPort() {
        return boundToAPort.remove(0);
    }
    public void setIsBoundToPort(ArrayList<Boolean> loopOccurrences) {
        boundToAPort = loopOccurrences;
    }

    private int callCountForAcceptConnectionAndReturnConnectedSokket = 0;
    public int getCallCountForAcceptConnectionAndReturnConnectedSokket() {
        return callCountForAcceptConnectionAndReturnConnectedSokket;
    }

    @Override
    public Sokket acceptConnectionAndReturnConnectedSokket() {
        callCountForAcceptConnectionAndReturnConnectedSokket += 1;
        return connectedSokket;
    }

    @Override
    public void close() {
        closed = true;
    }
}

