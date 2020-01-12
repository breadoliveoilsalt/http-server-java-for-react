package httpServer.logic;

import httpServer.factory.AppFactory;
import httpServer.wrappers.*;

import java.io.IOException;

public class ClientInitRunnable implements Runnable, HTTPServerLogicObject {

    private final Sokket sokket;
    private final AppFactory factory;

    public ClientInitRunnable(Sokket sokket, AppFactory factory) {
        this.sokket = sokket;
        this.factory = factory;
    }

    public void run() {
        try {
            printMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printMessage() throws IOException {
        Reader reader = new JavaBufferedReaderWrapper(sokket.getInputStream());
        String clientRequest = reader.readLine();
        System.out.println(clientRequest);

        Writer writer = new JavaPrintWriterWrapper(sokket.getOutputStream());
        writer.printLine("HTTP/1.1 200 OK\r\n");
        sokket.close();
    }

}
