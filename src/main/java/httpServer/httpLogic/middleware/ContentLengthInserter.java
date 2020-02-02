package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ContentLengthInserter extends Middleware {

    private Request request;
    private Response response;

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        if (response.file != null) {
            addContentLengthForFile();
        } else if (response.stringBody != null) {
            addContentLengthForStringBody();
        } else {
            addZeroContentLength();
        }
        passToNextMiddleware(request, response);
    }

    private void addContentLengthForFile() {
        response.addHeader(HTTPHeaders.ContentLength, Long.toString(response.file.length()));
    }

    private void addContentLengthForStringBody() {
        byte[] stringBodyBytes = response.stringBody.getBytes(StandardCharsets.UTF_8);
        response.addHeader(HTTPHeaders.ContentLength, Integer.toString(stringBodyBytes.length));
    }

    private void addZeroContentLength() {
        response.addHeader(HTTPHeaders.ContentLength, "0");
    }

}
