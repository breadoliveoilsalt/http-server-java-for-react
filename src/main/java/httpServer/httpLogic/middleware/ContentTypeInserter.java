package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.FileExtensions;
import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeInserter extends Middleware {

    private Response response;
    private Map<String, String> fileExtensionToContentTypeMap = new HashMap<>();

    public ContentTypeInserter() {
        fileExtensionToContentTypeMap.put(FileExtensions.TextFileExtension, HTTPContentTypes.TextPlain);
    }

    @Override
    public void handle(Request request, Response response) {
        this.response = response;
        if (responseHasOKStatusCode()) {
            if (response.stringBody != null) {
                addTextPlainContentType();
            } else if (response.file != null) {
                String fileName = response.file.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                response.addHeader(HTTPHeaders.ContentType, fileExtensionToContentTypeMap.get(fileExtension));
            }
        }
        passToNextMiddleware(request, response);
    }

    private boolean responseHasOKStatusCode() {
        return response.statusCode.equals(HTTPStatusCodes.OK);
    }

    private void addTextPlainContentType() {
        response.addHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextPlain);
    }

}
