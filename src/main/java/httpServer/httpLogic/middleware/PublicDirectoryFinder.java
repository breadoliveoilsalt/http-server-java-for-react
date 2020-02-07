package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.views.viewGenerators.DirectoryView;

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
            File directoryFile = new File(potentialPath);
            if (directoryFile.exists() && directoryFile.isDirectory()) {
                response.statusCode = HTTPStatusCodes.OK;
                getResponseBodyFor(directoryFile);
            }
        }
    }

    private void getResponseBodyFor(File directoryFile) {
        tryToAssignIndexDotHTMLFileToResponse(directoryFile);
        if (fileNotAssigned()) {
            generateFileListing(directoryFile);
        }
    }

    public void tryToAssignIndexDotHTMLFileToResponse(File directoryFile) {
        File indexDotHtmlFile = new File(directoryFile.getPath() + "/index.html");
        if (indexDotHtmlFile.exists() && indexDotHtmlFile.isFile()) {
            response.file = indexDotHtmlFile;
        }
    }

    private boolean fileNotAssigned() {
        return response.file == null;
    }

    private void generateFileListing(File directoryFile) {
        response.stringBody = new DirectoryView(request, directoryFile).render();
        System.out.println("Listings:");
        for (String fileName : directoryFile.list()) {
            System.out.println(fileName);
        }
    }

    public String getPublicRootPath() {
        return publicRootPath;
    }
    
}