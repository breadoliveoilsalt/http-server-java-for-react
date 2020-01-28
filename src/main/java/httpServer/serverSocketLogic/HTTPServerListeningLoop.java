package httpServer.serverSocketLogic;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.ServerSokket;
import httpServer.serverSocketLogic.wrappers.Sokket;

import java.io.IOException;

public class HTTPServerListeningLoop implements HTTPServerLogicObject {

    private final ServerSokket serverSokket;
    private final AppFactory factory;
    private ServerLogger logger;
    private Sokket connectedSokket;
    private Thread threadToStart;

    public HTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory, ServerLogger logger) {
        this.serverSokket = serverSokket;
        this.factory = factory;
        this.logger = logger;
    }

    public void run() throws IOException {
        while (serverSokket.isBoundToAPort()) {
            getSokketConnectedToClient();
            instantiateClientHandlerThread();
            startThread();
        }
    }

    private void getSokketConnectedToClient() throws IOException {
        connectedSokket = serverSokket.acceptConnectionAndReturnConnectedSokket();
    }

    private void instantiateClientHandlerThread() {
        Runnable clientHandler = factory.createClientHandlerRunnable(connectedSokket, logger);
        threadToStart = factory.createThreadFor(clientHandler);
    }

    private void startThread() {
        threadToStart.start();
    }

}
