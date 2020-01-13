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
        String[] requestLine = metaDataAndBody[0].split("\n");
        String method = requestLine[0].split(" ")[0];
        request.setMethod(method);
        return request;
    }
}
