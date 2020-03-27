import httpServer.serverSocketLogic.factory.HTTPServerAppFactory;
import httpServer.serverSocketLogic.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerInit;
import httpServer.serverLogger.ServerLogger;

import java.io.IOException;

import static java.lang.Integer.parseInt;

class App {

    public static void main(String[] args) throws IOException {
        int port = parseInt(args[0]);
        AppFactory factory = new HTTPServerAppFactory();
        ServerLogger logger = new ServerLogger(System.out);

        new HTTPServerInit(port, factory, logger).run();
    }

}