package httpServer.httpLogic.requests;

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

    public Request parse(String rawClientRequest) {
        this.rawClientRequest = rawClientRequest;
        parseRawRequestIntoSections();
        extractRequestLineForRequest();
        extractHeadersForRequest();
        extractBodyForRequest();
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
            Map<String, String> headersMap = new HashMap<>();
            loopThroughAndExtractRawHeaders(headersMap);
            request.setHeaders(headersMap);
        }
    }

    private void loopThroughAndExtractRawHeaders(Map<String, String> headers) {
        for (int i = 1; i < parsedRequestLineAndHeaders.length; i++) {
            String[] parsedKeyAndValue = parsedRequestLineAndHeaders[i].split(":");
            headers.put(parsedKeyAndValue[0], parsedKeyAndValue[1].trim());
        }
    }

    private void extractBodyForRequest() {
        if (parsedMetaDataAndBody.length > 1){
            request.setBody(parsedMetaDataAndBody[1]);
        }
    }

}
