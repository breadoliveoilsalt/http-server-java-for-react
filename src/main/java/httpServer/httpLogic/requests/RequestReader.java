package httpServer.httpLogic.requests;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.serverSocketLogic.wrappers.Sokket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

public class RequestReader {


//    private final static int defaultBufferSize = 8192;

//    public String readInputStream(Sokket sokket) throws IOException {
//        char[] readerBuffer = new char[defaultBufferSize];
//        Reader reader = new BufferedReader(new InputStreamReader(sokket.getInputStream()));
//        reader.read(readerBuffer, 0, defaultBufferSize);
//        return new String(readerBuffer).trim();
//    }

    private StringBuilder rawRequestBuilder;
    private Sokket sokket;
    private BufferedReader reader;
    private Request tempRequest;

    public RequestReader(Sokket sokket) throws IOException {
        this.sokket = sokket;
        this.rawRequestBuilder = new StringBuilder();
    }

    public String readInputStream() throws IOException {
        getReader();
        getMetadata();
        parseMetadata();
        checkForContentLength();
        return rawRequestBuilder.toString();
    }

    private void getReader() throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(sokket.getInputStream()));
    }

    private void getMetadata() throws IOException {
        try {
            sokket.setSoTimeout(5 * 1000);
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                rawRequestBuilder.append(currentLine + Whitespace.CRLF);
                if (endOfMetadataReached(currentLine)) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private boolean endOfMetadataReached(String currentLine) {
        return currentLine.equals("");
    }

    private void parseMetadata() {
        tempRequest = new RequestParser().parse(rawRequestBuilder.toString());
    }

    private void checkForContentLength() throws IOException {
        if (tempRequest.hasHeaderValue(HTTPHeaders.ContentLength)) {
            int contentLength = Integer.parseInt(tempRequest.getHeaderValue(HTTPHeaders.ContentLength));
            continueToRead(contentLength);
        }
    }

    private void continueToRead(int contentLength) throws IOException {
        char[] characterBuffer = new char[contentLength];
        reader.read(characterBuffer, 0 , contentLength);
        rawRequestBuilder.append(new String(characterBuffer));
    }

}
