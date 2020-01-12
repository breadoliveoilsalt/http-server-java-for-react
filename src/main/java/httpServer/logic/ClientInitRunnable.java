package httpServer.logic;

import httpServer.factory.AppFactory;
import httpServer.wrappers.JavaPrintWriterWrapper;
import httpServer.wrappers.Sokket;
import httpServer.wrappers.Writer;

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
        Writer writer = new JavaPrintWriterWrapper(sokket.getOutputStream());
        writer.printLine("Hey! You connected");
        sokket.close();
    }

}
