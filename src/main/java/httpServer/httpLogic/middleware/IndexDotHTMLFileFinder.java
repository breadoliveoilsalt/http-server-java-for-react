package httpServer.httpLogic.middleware;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class IndexDotHTMLFileFinder extends Middleware {

    private String path;
    private Request request;
    private Response response;

    public IndexDotHTMLFileFinder() {
        this.path = System.getProperty("user.dir");
    }

    public IndexDotHTMLFileFinder(String path) {
       this.path = path;
    }

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        if (response.statusCode == null) {
            checkForIndexDotHtmlRequest();
        }
        passToNextMiddleware(request, response);
    }

    private void checkForIndexDotHtmlRequest() {
        if (request.getPath().equals("/")) {
            File file = new File(path + "/index.html");
            if (file.exists()) {
                response.file = file;
//                response.httpVersion = "HTTP/1.1";
                response.statusCode = "200";
                response.statusMessage = "OK";
//                response.addHeader("Content-Length", Long.toString(file.length()));
            }
        }
    }

    public String getPath() {
        return path;
    }

}
