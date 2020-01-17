package httpServer.httpLogic.io;

import httpServer.wrappers.Sokket;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriter {

    public void writeToOutputStream(Sokket sokket, String writeableResponse) throws IOException {
        PrintWriter writer = new PrintWriter(sokket.getOutputStream(), true);
        writer.print(writeableResponse);
        writer.flush();
    }
}

