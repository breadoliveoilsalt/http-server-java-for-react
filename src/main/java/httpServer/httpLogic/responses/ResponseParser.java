package httpServer.httpLogic.responses;

import httpServer.httpLogic.constants.Whitespace;

public class ResponseParser {

    private Response response;
    private String stringifiedResponse;

    public String stringify(Response response) {
        this.response = response;
        stringifyStatusLine();
        addHeaders();
        addEndOfMetaData();
        addBody();
        return stringifiedResponse;
    }

    private void stringifyStatusLine() {
        stringifiedResponse = response.httpVersion + Whitespace.SPACE + response.statusCode + Whitespace.SPACE + response.statusMessage + Whitespace.CRLF;
    }

    private void addHeaders() {
        if (response.getHeaders() != null) {
            response.getHeaders().forEach(
                (key, value) -> stringifiedResponse += key + ": " + value + Whitespace.CRLF
            );
        }
    }

    private void addEndOfMetaData() {
        stringifiedResponse += Whitespace.CRLF;
    }

    private void addBody() {
       if (response.getBody() != null) {
           stringifiedResponse += response.body;
       }
    }
}
