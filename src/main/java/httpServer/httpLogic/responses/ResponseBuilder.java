package httpServer.httpLogic.responses;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    private final String httpVersion = "HTTP/1.1";
    private String statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private String body;

    public ResponseBuilder addOKStatusLine() {
        statusCode = "200";
        statusMessage = "OK";
        return this;
    }

    public ResponseBuilder addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder addBody(String body) {
        if (this.body == null) {
            this.body = "";
        }
        this.body += body;
        return this;
    }

    public Response build() {
        return new Response(httpVersion, statusCode, statusMessage, headers, body);
    }
}
