package httpServer.serverSocketLogic.wrappers;

import java.io.*;
import java.net.Socket;

public class JavaSocketWrapper implements Sokket {

    private final Socket socket;

    public JavaSocketWrapper(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }

}
