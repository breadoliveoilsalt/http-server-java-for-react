package httpServer.serverSocketLogic;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.ServerSokket;

import java.io.IOException;

public class HTTPServerInit implements HTTPServerLogicObject {

    private final int port;
    private HTTPServerLogicObject httpServerListeningLoop;
    private final AppFactory factory;
    private final ServerLogger logger;
    private ServerSokket serverSokket;

    public HTTPServerInit(int port, AppFactory factory, ServerLogger logger) {
        this.port = port;
        this.factory = factory;
        this.logger = logger;
    }

    public void run() throws IOException {

        try {
            instantiateServerSokket();
            instantiateHTTPServerListeningLoop();
            logger.logServerInit(port);
            runHTTPServerListeningLoop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.logServerShuttingDown();
            closeServerSokket();
        }

    }

    private void instantiateServerSokket() throws IOException {
        serverSokket = factory.createServerSokketListeningAtPort(port);
    }

    private void instantiateHTTPServerListeningLoop() {
        httpServerListeningLoop = factory.createHTTPServerListeningLoop(serverSokket, factory, logger);
    }

    private void runHTTPServerListeningLoop() throws IOException {
        httpServerListeningLoop.run();
    }

    private void closeServerSokket() throws IOException {
        serverSokket.close();
    }

}
