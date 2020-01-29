package httpServer.httpLogic.middleware;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class IndexDotHTMLFileFinder extends Middleware {
    
    public IndexDotHTMLFileFinder() { }
    
    @Override
    public void handle(Request request, Response response) {
        if (request.getPath() == "/") {
            String rootDirectory = System.getProperty("user.dir");
            File file = new File(rootDirectory + "/index.html");
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
