package httpServer.httpLogic.responses;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    private final String httpVersion = "HTTP/1.1";
    private String statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private String stringBody;

    public ResponseBuilder addStatusCode(String code) {
        statusCode = code;
        return this;
    }

    public ResponseBuilder addStatusMessage(String message) {
        statusMessage = message;
        return this;
    }

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

    public ResponseBuilder setHeaders(Map<String, String> headerMap) {
        headers = headerMap;
        return this;
    }

    public ResponseBuilder addBody(String body) {
        if (this.stringBody == null) {
            this.stringBody = "";
        }
        this.stringBody += body;
        return this;
    }

    public ResponseBuilder finalizeMetaDataForOKResponse() {
        addOKStatusLine();
        addContentLength();
        return this;
    }

    public ResponseBuilder addContentLength() {
        if (stringBody != null) {
            calculateContentLength();
        } else {
            addHeader("Content-Length", "0");
        }
        return this;
    }

    private void calculateContentLength() {
        byte[] responseBodyBytes = stringBody.getBytes(StandardCharsets.UTF_8);
        String contentLength = String.valueOf(responseBodyBytes.length);
        addHeader("Content-Length", contentLength);
    }

    public Response build() {
        return new Response(httpVersion, statusCode, statusMessage, headers, stringBody);
    }

}
