package chatServer.wrappers;

import java.io.IOException;
import java.net.ServerSocket;

public class JavaServerSocketWrapper implements ServerSokket {

    private ServerSocket serverSocket;

    public JavaServerSocketWrapper(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Sokket acceptConnectionAndReturnConnectedSokket() throws IOException {
        return new JavaSocketWrapper(serverSocket.accept());
    }

    @Override
    public boolean isBoundToAPort() {
        return serverSocket.isBound();
    }

    public void close() throws IOException {
        serverSocket.close();
    }

}
