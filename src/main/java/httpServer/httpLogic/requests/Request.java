package httpServer.httpLogic.requests;

import java.util.Map;

public class Request {

    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private float httpVersion;

    public float getHTTPVersion() {
        return httpVersion;
    }

    public void setHTTPVersion(float httpVersion) {
        this.httpVersion = httpVersion;
    }

    private Map<String,String> headers;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
