package httpServer.serverSocketLogic.wrappers;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class JavaSocketWrapper implements Sokket {

    private final Socket socket;

    public JavaSocketWrapper(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void setSoTimeout(int milliseconds) throws SocketException {
        socket.setSoTimeout(milliseconds);
    }

    public void close() throws IOException {
        socket.close();
    }

}
