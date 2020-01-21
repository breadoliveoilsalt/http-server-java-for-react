package httpServer.httpLogic.requests;

import java.util.Map;

public class Request {

    private final String method;
    private final String path;
    private final float httpVersion;
    private final Map<String, String> headers;
    private final String body;
    private final boolean isValid;

    public Request(
            String method,
            String path,
            float httpVersion,
            Map<String, String> headers,
            String body,
            boolean isValid) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
        this.isValid = isValid;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public float getHTTPVersion() {
        return httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public boolean isInvalid() {
        return !isValid;
    }
}
