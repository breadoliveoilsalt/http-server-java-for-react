package httpServer.factory;

import httpServer.serverSocketLogic.HTTPServerLogicObject;
import httpServer.wrappers.Reader;
import httpServer.wrappers.ServerSokket;
import httpServer.wrappers.Sokket;
import httpServer.wrappers.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface AppFactory {

    ServerSokket createServerSokketListeningAtPort(int port) throws IOException;

    Reader createReader(InputStream inputStream);

    Writer createWriter(OutputStream outputStream);

    Thread createThreadFor(Runnable runnable);

    HTTPServerLogicObject createHTTPServerListeningLoop(ServerSokket serverSokket, AppFactory factory);

    Runnable createClientInitRunnable(Sokket sokket, AppFactory factory);

}
