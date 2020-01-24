package httpServer.serverSocketLogic;

import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.wrappers.ServerSokket;

import java.io.IOException;

public class HTTPServerInit implements HTTPServerLogicObject {

    private final int port;
    private HTTPServerLogicObject httpServerListeningLoop;
    private final AppFactory factory;
    private ServerSokket serverSokket;

    public HTTPServerInit(int port, AppFactory factory) {
        this.port = port;
        this.factory = factory;
    }

    public void run() throws IOException {

        try {
            instantiateServerSokket();
            instantiateHTTPServerListeningLoop();
            runHTTPServerListeningLoop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServerSokket();
        }

    }

    private void instantiateServerSokket() throws IOException {
        serverSokket = factory.createServerSokketListeningAtPort(port);
    }

    private void instantiateHTTPServerListeningLoop() {
        httpServerListeningLoop = factory.createHTTPServerListeningLoop(serverSokket, factory);
    }

    private void runHTTPServerListeningLoop() throws IOException {
        httpServerListeningLoop.run();
    }

    private void closeServerSokket() throws IOException {
        serverSokket.close();
    }

}
