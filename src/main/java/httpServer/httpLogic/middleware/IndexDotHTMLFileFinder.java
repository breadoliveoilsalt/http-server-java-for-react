package httpServer.httpLogic.middleware;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class IndexDotHTMLFileFinder extends Middleware {

    private String path;

    public IndexDotHTMLFileFinder() {
        this.path = System.getProperty("user.dir");
    }

    public IndexDotHTMLFileFinder(String path) {
       this.path = path;
    }

    @Override
    public void handle(Request request, Response response) {
        if (request.getPath().equals("/")) {
            File file = new File(path + "/index.html");
            if (file.exists()) {
                response.file = file;
                response.statusCode = "200";
                response.statusMessage = "OK";
                response.addHeader("Content-Length", Long.toString(file.length()));
            }
        } else {
            passToNextMiddleware(request, response);
        }
    }

    public String getPath() {
        return path;
    }

}
