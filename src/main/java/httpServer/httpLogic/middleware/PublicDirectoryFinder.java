package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.views.viewGenerators.DirectoryView;
import httpServer.httpLogic.views.viewGenerators.ViewGenerator;

import java.io.File;

public class PublicDirectoryFinder extends Middleware {

    private Request request;
    private Response response;
    private final String publicRootPath;
    private ViewGenerator viewGenerator;

    public PublicDirectoryFinder() {
        String rootDirectory = System.getProperty("user.dir");
        this.publicRootPath = rootDirectory + "/public";
    }

    public PublicDirectoryFinder(String publicFilesPath) {
        this.publicRootPath = publicFilesPath;
    }

    public PublicDirectoryFinder(String publicFilesPath, ViewGenerator viewGenerator) {
        this.publicRootPath = publicFilesPath;
        this.viewGenerator = viewGenerator;
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
            String potentialPath = determinePotentialPath();
            File directoryFile = new File(potentialPath);
            if (directoryFile.exists() && directoryFile.isDirectory()) {
                response.statusCode = HTTPStatusCodes.OK;
                getResponseBodyFor(directoryFile);
            }
        }
    }

    private String determinePotentialPath() {
        if (request.getPath().equals("/")) {
            return publicRootPath;
        } else {
            return publicRootPath + request.getPath();
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
            response.addHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML);
        }
    }

    private boolean fileNotAssigned() {
        return response.file == null;
    }

    private void generateFileListing(File directoryFile) {
        assignDefaultViewGeneratorIfNeeded(directoryFile);
        response.stringBody = viewGenerator.render();
        response.addHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML);
    }

    private void assignDefaultViewGeneratorIfNeeded(File directoryFile) {
        if (viewGenerator == null) {
            viewGenerator = new DirectoryView(request, directoryFile);
        }
    }

    public String getPublicRootPath() {
        return publicRootPath;
    }

    public ViewGenerator getViewGenerator() {
        return viewGenerator;
    }

}