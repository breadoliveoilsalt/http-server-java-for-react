package httpServer.httpLogic.requests;

public class RequestParser {

    private final RequestBuilder requestBuilder;
    private String rawClientRequest;
    private String[] parsedMetaDataAndBody;
    private String[] parsedRequestLineAndHeaders;
    private String[] parsedRequestLine;

    public RequestParser() {
        requestBuilder = new RequestBuilder();
    }

    public Request parse(String rawClientRequest) {
        try {
            this.rawClientRequest = rawClientRequest;
            parseRawRequestIntoSections();
            extractRequestLineForRequest();
            extractHeadersForRequest();
            extractBodyForRequest();
        } catch (Exception e) {
            requestBuilder.flagAsInvalid();
        }

        return requestBuilder.build();
    }

    private void parseRawRequestIntoSections() {
        parsedMetaDataAndBody = rawClientRequest.split("\r\n");
        parsedRequestLineAndHeaders = parsedMetaDataAndBody[0].split("\n");
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
        if (parsedMetaDataAndBody.length > 1){
            requestBuilder.addBody(parsedMetaDataAndBody[1]);
        }
    }

}
