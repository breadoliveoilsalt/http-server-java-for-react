package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class FileFinder extends Middleware {

    private String basePath;
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
        if (response.hasUndeterminedStatus()) {
            this.request = request;
            this.response = response;
            checkForFile();
        }
        passToNextMiddleware(request, response);
    }

    private void checkForFile() {
        if (request.getHTTPMethod().equals(HTTPMethods.GET)) {
            File file = new File(basePath + request.getPath());
            System.out.println("Looking for file...");

            if (file.exists() && file.isFile()) {
                System.out.println("File Found");
                response.statusCode = HTTPStatusCodes.OK;
                response.file = file;
            }
        }
    }

    public String getBasePath() {
        return basePath;
    }

}
