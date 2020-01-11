package httpServer.wrappers;

import java.io.OutputStream;
import java.io.PrintWriter;

public class JavaPrintWriterWrapper implements Writer {

    private final PrintWriter writer;

    public JavaPrintWriterWrapper(OutputStream outputStream) {
        writer = new PrintWriter(outputStream, true);
    }

    @Override
    public void printLine(String message) {
        writer.println(message);
    }

}
