package httpServer.httpLogic.responses;

import httpServer.httpLogic.Constants;

import java.util.Iterator;
import java.util.Map;

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
            Iterator it = response.getHeaders().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                stringifiedResponse += pair.getKey() + ": " + pair.getValue() + Constants.CRLF;
                it.remove();
            }
        }
    }

    private void addEndOfMetaData() {
        stringifiedResponse += Constants.CRLF;
    }

    private void addBody() {
       if (response.body != null) {
           stringifiedResponse += response.body;
       }
    }
}
