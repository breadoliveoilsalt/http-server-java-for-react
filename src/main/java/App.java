import httpServer.serverSocketLogic.factory.HTTPServerAppFactory;
import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerInit;

import java.io.IOException;

class App {

    public static void main(String[] args) throws IOException {
        int port = 5000;
        AppFactory factory = new HTTPServerAppFactory();

        new HTTPServerInit(port, factory).run();
    }

}