package httpServer.serverSocketLogic.factory;

import httpServer.httpLogic.ClientHandlerRunnable;
import httpServer.serverSocketLogic.*;
import httpServer.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.*;

import java.io.IOException;

public class HTTPServerAppFactory implements AppFactory {

    public JavaServerSocketWrapper createServerSokketListeningAtPort(int port) throws IOException {
        return new JavaServerSocketWrapper(port);
    }

    public Thread createThreadFor(Runnable runnable) {
        return new Thread(runnable);
    }

    public HTTPServerLogicObject createHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory, ServerLogger logger) { return new HTTPServerListeningLoop(serverSokket, factory, logger);
    }

    public Runnable createClientHandlerRunnable(Sokket sokket, ServerLogger logger) {
        return new ClientHandlerRunnable(sokket, logger);
    }

}
