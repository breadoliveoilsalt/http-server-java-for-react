package httpServer.factory;

import httpServer.serverSocketLogic.HTTPServerLogicObject;
import httpServer.wrappers.ServerSokket;
import httpServer.wrappers.Sokket;

import java.io.IOException;

public interface AppFactory {

    ServerSokket createServerSokketListeningAtPort(int port) throws IOException;

    Thread createThreadFor(Runnable runnable);

    HTTPServerLogicObject createHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory);

    Runnable createClientInitRunnable(Sokket sokket);

}
