package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.constants.HTTPStatusMessages;
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
        if (response.hasUndeterminedStatus()) {
            this.request = request;
            this.response = response;
            checkForIndexDotHtmlRequest();
        }
        passToNextMiddleware(request, response);
    }

    private void checkForIndexDotHtmlRequest() {
        if (request.getPath().equals("/")) {
            File file = new File(path + "/index.html");
            if (file.exists()) {
                response.statusCode = HTTPStatusCodes.OK;
                response.file = file;
                // GOT TO MOVE THIS TO HEADER MIDDLEWARE
                response.addHeader(HTTPHeaders.ContentLength, Long.toString(file.length()));
            }
        }
    }

    public String getPath() {
        return path;
    }

}
