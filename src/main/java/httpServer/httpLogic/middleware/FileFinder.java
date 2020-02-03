package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class FileFinder extends Middleware {

    private final String basePath;
    private Request request;
    private Response response;

    public FileFinder() {
        this.basePath = System.getProperty("user.dir");
    }

    public FileFinder(String basePath) {
       this.basePath = basePath;
    }

    @Override
    public void handle(Request request, Response response) {
        if (response.hasUndeterminedStatusCode()) {
            this.request = request;
            this.response = response;
            checkForFile();
        }
        passToNextMiddleware(request, response);
    }

    private void checkForFile() {
        if (request.getHTTPMethod().equals(HTTPMethods.GET)) {
            File file = new File(basePath + request.getPath());
            if (file.exists() && file.isFile()) {
                response.statusCode = HTTPStatusCodes.OK;
                response.file = file;
            }
        }
    }

    public String getBasePath() {
        return basePath;
    }

}
