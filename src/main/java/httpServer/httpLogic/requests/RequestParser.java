package httpServer.httpLogic.requests;

import httpServer.httpLogic.constants.HTTPStatusMessages;
import httpServer.httpLogic.constants.Whitespace;

public class RequestParser {

    private final RequestBuilder requestBuilder;
    private String rawClientRequest;
    private String[] parsedMetadataAndBody;
    private String[] parsedRequestLineAndHeaders;
    private String[] parsedRequestLine;

    public RequestParser() {
        requestBuilder = new RequestBuilder();
    }

    public Request parse(String rawClientRequest) {
        this.rawClientRequest = rawClientRequest;
        if (rawClientRequest.equals(HTTPStatusMessages.RequestTimeout)) {
            requestBuilder.flagAsTimedOut();
        } else {
            attemptToParseRequest();
        }
        return requestBuilder.build();
    }

    private void attemptToParseRequest() {
        try {
            parseRawRequestIntoSections();
            extractRequestLineForRequest();
            extractHeadersForRequest();
            extractBodyForRequest();
        } catch (Exception e) {
            requestBuilder.flagAsUnparsable();
        }
    }

    private void parseRawRequestIntoSections() {
        parsedMetadataAndBody = rawClientRequest.split(Whitespace.CRLF + Whitespace.CRLF);
        parsedRequestLineAndHeaders = parsedMetadataAndBody[0].split(Whitespace.CRLF);
        parsedRequestLine = parsedRequestLineAndHeaders[0].split(" ");
    }

    private void extractRequestLineForRequest() {
        requestBuilder.addMethod(parsedRequestLine[0]);
        requestBuilder.addPath(parsedRequestLine[1]);
        float httpVersion = Float.valueOf(parsedRequestLine[2].split("/")[1]);
        requestBuilder.addHTTPVersion(httpVersion);
    }

    private void extractHeadersForRequest() {
        if (parsedRequestLineAndHeaders.length > 1) {
            loopThroughAndExtractRawHeaders();
        }
    }

    private void loopThroughAndExtractRawHeaders() {
        for (int i = 1; i < parsedRequestLineAndHeaders.length; i++) {
            String[] parsedKeyAndValue = parsedRequestLineAndHeaders[i].split(":");
            requestBuilder.addHeader(parsedKeyAndValue[0], parsedKeyAndValue[1].trim());
        }
    }

    private void extractBodyForRequest() {
        if (parsedMetadataAndBody.length > 1){
            requestBuilder.addBody(parsedMetadataAndBody[1]);
        }
    }

}
