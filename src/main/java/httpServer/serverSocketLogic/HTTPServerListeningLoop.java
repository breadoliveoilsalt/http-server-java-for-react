package httpServer.serverSocketLogic;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.wrappers.ServerSokket;
import httpServer.serverSocketLogic.wrappers.Sokket;

import java.io.IOException;

public class HTTPServerListeningLoop implements HTTPServerLogicObject {

    private final ServerSokket serverSokket;
    private final AppFactory factory;
    private Sokket connectedSokket;
    private Thread threadToStart;

    public HTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory) {
        this.serverSokket = serverSokket;
        this.factory = factory;
    }

    public void run() throws IOException {
        while (serverSokket.isBoundToAPort()) {
            getSokketConnectedToClient();
            instantiateClientInitThread();
            startThread();
        }
    }

    private void getSokketConnectedToClient() throws IOException {
        connectedSokket = serverSokket.acceptConnectionAndReturnConnectedSokket();
    }

    private void instantiateClientInitThread() {
        Runnable clientInit = factory.createClientInitRunnable(connectedSokket);
        threadToStart = factory.createThreadFor(clientInit);
    }

    private void startThread() {
        threadToStart.start();
    }

}
