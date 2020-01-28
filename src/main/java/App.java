import httpServer.serverSocketLogic.factory.HTTPServerAppFactory;
import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerInit;
import httpServer.serverSocketLogic.serverLogger.ServerLogger;

import java.io.IOException;

class App {

    public static void main(String[] args) throws IOException {
        int port = 5000;
        AppFactory factory = new HTTPServerAppFactory();
        ServerLogger logger = new ServerLogger(System.out);

        new HTTPServerInit(port, factory, logger).run();
    }

}