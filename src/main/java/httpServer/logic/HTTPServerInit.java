package httpServer.logic;

import httpServer.factory.AppFactory;
import httpServer.wrappers.ServerSokket;

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
            instantiateChatServerListeningLoop();
            runChatServerListeningLoop();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServerSokket();
        }

    }

    private void instantiateServerSokket() throws IOException {
        serverSokket = factory.createServerSokketListeningAtPort(port);
    }

    private void instantiateChatServerListeningLoop() {
        httpServerListeningLoop = factory.createHTTPServerListeningLoop(serverSokket, factory);
    }

    private void runChatServerListeningLoop() throws IOException {
        httpServerListeningLoop.run();
    }

    private void closeServerSokket() throws IOException {
        serverSokket.close();
    }

}
