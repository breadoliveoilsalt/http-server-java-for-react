package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class PublicDirectoryFinder extends Middleware {

    private Request request;
    private Response response;
    private final String publicRootPath;

    public PublicDirectoryFinder() {
        String rootDirectory = System.getProperty("user.dir");
        this.publicRootPath = rootDirectory + "/public";
    }

    public PublicDirectoryFinder(String publicFilesPath) {
        this.publicRootPath = publicFilesPath;
    }

    @Override
    public void handle(Request request, Response response) {
        if (response.hasUndeterminedStatusCode()) {
            this.request = request;
            this.response = response;
            checkForDirectory();
        }
        passToNextMiddleware(request, response);
    }

    private void checkForDirectory() {
        if (request.getHTTPMethod().equals(HTTPMethods.GET)) {
            String potentialPath = publicRootPath + request.getPath();
            File file = new File(potentialPath);
            if (file.exists() && file.isDirectory()) {
                response.statusCode = HTTPStatusCodes.OK;
                lookForIndexDotHTMLFile(potentialPath);
                // do more stuff - render directory listing
            }
        }
    }

    private void lookForIndexDotHTMLFile(String potentialPath) {
        File file = new File(potentialPath + "/index.html");
        if (file.exists() && file.isFile()) {
            response.file = file;
        }
    }

    public String getPublicRootPath() {
        return publicRootPath;
    }
}