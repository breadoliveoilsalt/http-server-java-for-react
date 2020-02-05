package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class PublicFileFinder extends Middleware {

    private final String publicFilesPath;
    private Request request;
    private Response response;

    public PublicFileFinder() {
        String rootDirectory = System.getProperty("user.dir");
        this.publicFilesPath = rootDirectory + "/public";
    }

    public PublicFileFinder(String publicFilesPath) {
       this.publicFilesPath = publicFilesPath;
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
            File file = new File(publicFilesPath + request.getPath());
            if (file.exists() && file.isFile()) {
                response.statusCode = HTTPStatusCodes.OK;
                response.file = file;
            }
        }
    }

    public String getPublicFilesPath() {
        return publicFilesPath;
    }

}
