package httpServer.httpLogic;

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

        // add html version
        // have version number return a float
//        parseHeaders(parsedMetaData);
//        String rawHeaders = parsedMetaData[1];
//        request.setHTTPVersion(rawHeaders);
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
    private static void parseHeaders(String[] parsedMetaDataAndBody) {

    }
}
