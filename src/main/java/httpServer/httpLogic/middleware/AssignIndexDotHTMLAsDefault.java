package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class AssignIndexDotHTMLAsDefault extends Middleware {

    private Request request;
    private Response response;
    private final String publicRootPath;

    public AssignIndexDotHTMLAsDefault() {
        String rootDirectory = System.getProperty("user.dir");
        this.publicRootPath = rootDirectory + "/public";
    }

    public AssignIndexDotHTMLAsDefault(String publicFilesPath) {
        this.publicRootPath = publicFilesPath;
    }

    @Override
    public void handle(Request request, Response response) {
        if (response.hasUndeterminedStatusCode()) {
            this.request = request;
            this.response = response;
            assignIndexDotHTMLFileToResponse();
        }
        passToNextMiddleware(request, response);
    }

    public void assignIndexDotHTMLFileToResponse() {
        File indexDotHtmlFile = new File(publicRootPath + "/index.html");
        response.file = indexDotHtmlFile;
        response.addHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML);
    }
}
