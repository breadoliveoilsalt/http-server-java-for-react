package httpServer.httpLogic;

public class RequestParser {

//    private Request request;
//
//    RequestParser() {
//        this.request = new Request();
//    }

    public static Request parse(String rawClientRequest) {
        Request request = new Request();
        String[] metaDataAndBody = rawClientRequest.split("\r\n");
        String[] parsedRequestLine = metaDataAndBody[0].split("\n")[0].split(" ");
//        String method = requestLine[0].split(" ")[0];
        String method = parsedRequestLine[0];
        request.setMethod(method);
        String path = parsedRequestLine[1];
        request.setPath(path);
        request.setHTTPVersion(parsedRequestLine[2]);
        // add html version
        // have version number return a float
        return request;
    }
}
