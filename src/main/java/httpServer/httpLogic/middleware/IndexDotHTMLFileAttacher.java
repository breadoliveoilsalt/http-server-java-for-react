package httpServer.httpLogic.middleware;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class IndexDotHTMLFileAttacher extends Middleware {
    
    private String pathOfFile = "../../../../index.html";
    
    public IndexDotHTMLFileAttacher() { }
    
    public IndexDotHTMLFileAttacher(String pathOfFile) {
        this.pathOfFile = pathOfFile;
    }
        

    @Override
    public void handle(Request request, Response response) {
        if (request.getPath() == "/") {
            File file = new File(pathOfFile);
            if (file.exists()) {
                response.file = file;
                response.statusCode = "200";
                response.statusMessage = "OK";
            }
        } else {
            passToNextMiddleware(request, response);
        }
    }

}
