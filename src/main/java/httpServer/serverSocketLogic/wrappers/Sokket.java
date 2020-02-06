package httpServer.serverSocketLogic.wrappers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

public interface Sokket {

   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream() throws IOException;

   void setSoTimeout(int milliseconds) throws SocketException;

   void close() throws IOException;

}
