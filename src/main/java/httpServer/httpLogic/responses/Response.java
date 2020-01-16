package httpServer.httpLogic.responses;

import java.util.Map;

public class Response {
    protected String httpVersion;
    protected String statusCode;
    protected String statusMessage;
    protected Map<String, String> headers;
    protected String body;

    public Response(String httpVersion,
                    String statusCode,
                    String statusMessage,
                    Map<String, String> headers,
                    String body) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = headers;
        this.body = body;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

}
