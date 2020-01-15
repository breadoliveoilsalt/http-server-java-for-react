package httpServer.httpLogic;

import httpServer.wrappers.Sokket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestReader {

    final static int defaultBufferSize = 8192;

    public String readInputStream(Sokket sokket) throws IOException {
        char[] readerBuffer = new char[defaultBufferSize];
        Reader reader = new BufferedReader(new InputStreamReader(sokket.getInputStream()));
        reader.read(readerBuffer, 0, defaultBufferSize);
        return new String(readerBuffer).trim();
    }
}
