package httpServer.httpLogic.responses;

import java.io.File;
import java.util.Map;

public class Response {

    public String httpVersion;
    public String statusCode;
    public String statusMessage;
    public Map<String, String> headers;
    public String stringBody;
    public File file;

    public Response() {}

    public Response(String httpVersion,
                    String statusCode,
                    String statusMessage,
                    Map<String, String> headers,
                    String stringBody) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = headers;
        this.stringBody = stringBody;
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

    public boolean hasHeader(String key, String value) {
        return headers.get(key).equals(value);
    }

    public String getHeaderValueFor(String key) {
        return headers.get(key);
    }

    public String getStringBody() {
        return stringBody;
    }

}
