package httpServer.httpLogic.requests;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusMessages;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.serverSocketLogic.wrappers.Sokket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

public class RequestReader {

    private final StringBuilder rawRequestBuilder;
    private final Sokket sokket;
    private BufferedReader reader;
    private Request tempRequest;

    public RequestReader(Sokket sokket) {
        this.sokket = sokket;
        this.rawRequestBuilder = new StringBuilder();
    }

    public String readInputStream() throws IOException {
        try {
            sokket.setSoTimeout(5 * 1000);
            getReader();
            getMetadata();
            parseMetadata();
            checkForContentLength();
        } catch (SocketTimeoutException e) {
            rawRequestBuilder.delete(0, rawRequestBuilder.length());
            rawRequestBuilder.append(HTTPStatusMessages.RequestTimeout);
        } finally {
            return rawRequestBuilder.toString();
        }
    }

    private void getReader() throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(sokket.getInputStream()));
    }

    private void getMetadata() throws IOException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            rawRequestBuilder.append(currentLine + Whitespace.CRLF);
            if (endOfMetadataReached(currentLine)) {
                break;
            }
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
