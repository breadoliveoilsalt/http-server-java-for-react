package httpServer.factory;

import httpServer.httpLogic.ClientHandlerRunnable;
import httpServer.serverSocketLogic.*;
import httpServer.wrappers.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTTPServerAppFactory implements AppFactory {

    public JavaServerSocketWrapper createServerSokketListeningAtPort(int port) throws IOException {
        return new JavaServerSocketWrapper(port);
    }

    public JavaBufferedReaderWrapper createReader(InputStream inputStream) {
        return new JavaBufferedReaderWrapper(inputStream);
    }

    public JavaPrintWriterWrapper createWriter(OutputStream outputStream) {
        return new JavaPrintWriterWrapper(outputStream);
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
