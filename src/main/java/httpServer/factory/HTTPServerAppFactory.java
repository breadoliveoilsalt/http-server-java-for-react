package httpServer.factory;

import httpServer.httpLogic.runnable.ClientHandlerRunnable;
import httpServer.serverSocketLogic.*;
import httpServer.wrappers.*;

import java.io.IOException;

public class HTTPServerAppFactory implements AppFactory {

    public JavaServerSocketWrapper createServerSokketListeningAtPort(int port) throws IOException {
        return new JavaServerSocketWrapper(port);
    }

    public Thread createThreadFor(Runnable runnable) {
        return new Thread(runnable);
    }

    public HTTPServerLogicObject createHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory) { return new HTTPServerListeningLoop(serverSokket, factory);
    }

    public Runnable createClientInitRunnable(Sokket sokket, AppFactory factory) {
        return new ClientHandlerRunnable(sokket, factory);
    }

}
