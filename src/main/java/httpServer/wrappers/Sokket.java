package httpServer.wrappers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Sokket {

   InputStream getInputStream() throws IOException;

   OutputStream getOutputStream() throws IOException;

   void close() throws IOException;

}
