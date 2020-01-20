package httpServer.httpLogic.responses;

import httpServer.httpLogic.Constants;

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
        stringifiedResponse = response.httpVersion + Constants.SPACE + response.statusCode + Constants.SPACE + response.statusMessage + Constants.CRLF;
    }

    private void addHeaders() {
        if (response.getHeaders() != null) {
            response.getHeaders().forEach(
                (key, value) -> stringifiedResponse += key + ": " + value + Constants.CRLF
            );
        }
    }

    private void addEndOfMetaData() {
        stringifiedResponse += Constants.CRLF;
    }

    private void addBody() {
       if (response.getBody() != null) {
           stringifiedResponse += response.body;
       }
    }
}
