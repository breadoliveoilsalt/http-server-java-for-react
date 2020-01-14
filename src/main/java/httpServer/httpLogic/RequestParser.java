package httpServer.httpLogic;

import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    private Request request;
    private String rawClientRequest;
    private String[] parsedMetaDataAndBody;
    private String[] parsedRequestLineAndHeaders;
    private String[] parsedRequestLine;

    public RequestParser() {
        request = new Request();
    }

// this should throw something, no? Request not understood?
    public Request parse(String rawClientRequest) {
        this.rawClientRequest = rawClientRequest;
        parseRawRequestIntoSections();
        extractRequestLineForRequest();
        extractHeadersForRequest();

        // add html version
        // have version number return a float
//        parseHeaders(parsedMetaData);
//        String rawHeaders = parsedMetaData[1];
//        request.setHTTPVersion(rawHeaders}jk);
        return request;
    }

    private void parseRawRequestIntoSections() {
        parsedMetaDataAndBody = rawClientRequest.split("\r\n");
        parsedRequestLineAndHeaders = parsedMetaDataAndBody[0].split("\n");
        parsedRequestLine = parsedRequestLineAndHeaders[0].split(" ");
    }
    private void extractRequestLineForRequest() {
        request.setMethod(parsedRequestLine[0]);
        request.setPath(parsedRequestLine[1]);
        float httpVersion = Float.valueOf(parsedRequestLine[2].split("/")[1]);
        request.setHTTPVersion(httpVersion);

    }

    private void extractHeadersForRequest() {
        if (parsedRequestLineAndHeaders.length > 1) {
            Map<String, String> headers = new HashMap<>();
            for (int i = 1; i < parsedRequestLineAndHeaders.length; i++) {
                String[] parsedKeyAndValue = parsedRequestLineAndHeaders[i].split(" ");
                headers.put(parsedKeyAndValue[0], parsedKeyAndValue[1]);
            }
            request.setHeaders(headers);
        }
    }

}
