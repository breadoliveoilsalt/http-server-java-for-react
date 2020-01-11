package httpServer.factory;

import httpServer.logic.ChatServerLogicObject;
import httpServer.wrappers.Reader;
import httpServer.wrappers.ServerSokket;
import httpServer.wrappers.Sokket;
import httpServer.wrappers.Writer;
import httpServer.models.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface AppFactory {

    ServerSokket createServerSokketListeningAtPort(int port) throws IOException;

    Reader createReader(InputStream inputStream);

    Writer createWriter(OutputStream outputStream);

    Thread createThreadFor(Runnable runnable);

    ChatServerLogicObject createChatServerListeningLoop(ServerSokket serverSokket, ChatRoom chatRoom, AppFactory factory);

    ChatRoom createChatRoom(AppFactory factory);

    Runnable createClientInitRunnable(Sokket sokket, ChatRoom chatRoom, AppFactory factory);

    Client createClient(Sokket sokket, AppFactory factory) throws IOException;

    Runnable createListenForClientMessageRunnable(Client client, ChatRoom chatRoom);

}
