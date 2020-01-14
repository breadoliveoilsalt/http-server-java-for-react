package httpServer.httpLogic;

public class ResponseConverter {

    private Response response;
    private String stringifiedReponse;
    private final String SPACE = " ";
    private final String NEW_LINE = "\n";
    private final String CRLF = "\r\n";

    public String stringify(Response response) {
        this.response = response;
        stringifyStatusLine();
        addEndOfResponse();
        return stringifiedReponse;
    }

    private void stringifyStatusLine() {
        stringifiedReponse = response.httpVersion + SPACE + response.statusCode + SPACE + response.statusMessage;
    }

    private void addEndOfResponse() {
        stringifiedReponse += CRLF;
    }
}
