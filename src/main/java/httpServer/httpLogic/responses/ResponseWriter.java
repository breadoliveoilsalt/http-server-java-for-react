package httpServer.httpLogic.responses;

import httpServer.serverSocketLogic.wrappers.Sokket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ResponseWriter {

    public void writeToOutputStream(Sokket sokket, String writeableResponse) throws IOException {
        PrintWriter writer = new PrintWriter(sokket.getOutputStream(), true);
        writer.print(writeableResponse);
        writer.flush();
    }

    public void writeToOutputStream(Sokket sokket, byte[] rawByteArrayResponse) throws IOException {
        OutputStream sokketOutputStream = sokket.getOutputStream();
        sokketOutputStream.write(rawByteArrayResponse);
        sokketOutputStream.flush();
    }
}

