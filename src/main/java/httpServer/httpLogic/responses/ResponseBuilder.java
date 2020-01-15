package httpServer.httpLogic.responses;

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

    public Response build() {
        return new Response(httpVersion, statusCode, statusMessage, headers, body);
    }
}
