package httpServer.factory;

import httpServer.logic.*;
import httpServer.models.*;
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

    public ChatServerLogicObject createChatServerListeningLoop(ServerSokket serverSokket, ChatRoom chatRoom, AppFactory factory) { return new HTTPServerListeningLoop(serverSokket, chatRoom, factory);
    }

    public ChatRoom createChatRoom(AppFactory factory) {
        return new ChatRoom(factory);
    }

    public Runnable createClientInitRunnable(Sokket sokket, ChatRoom chatRoom, AppFactory factory) {
        return new ClientInitRunnable(sokket, chatRoom, factory);
    }

    public Client createClient(Sokket sokket, AppFactory factory) throws IOException {
        return new Client(sokket, factory);
    }

    public Runnable createListenForClientMessageRunnable(Client client, ChatRoom chatRoom) {
        return new ListenForClientMessageRunnable(client, chatRoom);
    }


}
