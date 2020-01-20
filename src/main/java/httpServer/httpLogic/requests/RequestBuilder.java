package httpServer.httpLogic.requests;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {
    
    private String method;
    private String path;
    private float httpVersion;
    private Map<String, String> headers;
    private String body;
    private boolean isValid = true;

    public RequestBuilder addMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestBuilder addPath(String path) {
        this.path = path;
        return this;
    }

    public RequestBuilder addHTTPVersion(float version) {
        this.httpVersion = version;
        return this;
    }

    public RequestBuilder addHeader(String key, String value) {
        if (this.headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    public RequestBuilder setHeaders(Map<String, String> headerMap) {
        headers = headerMap;
        return this;
    }

    public RequestBuilder addBody(String body) {
        if (this.body == null) {
            this.body = "";
        }
        this.body += body;
        return this;
    }

    public RequestBuilder flagAsInvalid() {
        isValid = false;
        return this;
    }

    public Request build() {
        return new Request(method, path, httpVersion, headers, body, isValid);
    }

}
