package httpServer.httpLogic.requests;

import java.util.Map;

public class Request {

    private final String httpMethod;
    private final String path;
    private final float httpVersion;
    private final Map<String, String> headers;
    private final String body;
    private final boolean wasParsable;

    public Request(
            String httpMethod,
            String path,
            float httpVersion,
            Map<String, String> headers,
            String body,
            boolean wasParsable) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
        this.wasParsable = wasParsable;
    }

    public String getHTTPMethod() {
        return httpMethod;
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

    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public String getBody() {
        return body;
    }

    public boolean wasUnparsable() {
        return !wasParsable;
    }

}
