package httpServer.httpLogic;

import httpServer.factory.AppFactory;
import httpServer.serverSocketLogic.HTTPServerLogicObject;
import httpServer.wrappers.*;

import java.io.IOException;

public class ClientHandlerRunnable implements Runnable, HTTPServerLogicObject {

    private final Sokket sokket;
    private final AppFactory factory;

    public ClientHandlerRunnable(Sokket sokket, AppFactory factory) {
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
//        Reader reader = new JavaBufferedReaderWrapper(sokket.getInputStream());
//        String clientRequest = reader.readLine();
//        System.out.println(clientRequest);

        String clientRequest = ClientRequestReader.readInputStream(sokket);
        System.out.println(clientRequest);

        Writer writer = new JavaPrintWriterWrapper(sokket.getOutputStream());
        writer.printLine("HTTP/1.1 200 OK\r\n");
        sokket.close();
    }

}
