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
        populateFileExtensionToContentTypeMap();
    }

    @Override
    public void handle(Request request, Response response) {
        this.response = response;
        if (responseHasOKStatusCode()) {
            if (response.stringBody != null) {
                addContentTypeHeader(HTTPContentTypes.TextPlain);
            } else if (response.file != null) {
                assignContentTypeBasedOnFileExtension();
            }
        }
        passToNextMiddleware(request, response);
    }

    private boolean responseHasOKStatusCode() {
        return response.statusCode.equals(HTTPStatusCodes.OK);
    }

    public void assignContentTypeBasedOnFileExtension() {
        String fileExtension = getFileExtension();
        String contentType = fileExtensionToContentTypeMap.get(fileExtension);
        addContentTypeHeader(contentType);
    }

    private String getFileExtension() {
        String fileName = response.file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void addContentTypeHeader(String contentType) {
        response.addHeader(HTTPHeaders.ContentType, contentType);
    }

    private void populateFileExtensionToContentTypeMap() {
        fileExtensionToContentTypeMap.put(FileExtensions.TextExtension, HTTPContentTypes.TextPlain);
        fileExtensionToContentTypeMap.put(FileExtensions.HTMLExtension, HTTPContentTypes.TextHTML);
        fileExtensionToContentTypeMap.put(FileExtensions.HTMExtension, HTTPContentTypes.TextHTML);
        fileExtensionToContentTypeMap.put(FileExtensions.CSSExtension, HTTPContentTypes.TextCSS);
        fileExtensionToContentTypeMap.put(FileExtensions.JSExtension, HTTPContentTypes.TextJavaScript);
        fileExtensionToContentTypeMap.put(FileExtensions.JSONExtension, HTTPContentTypes.ApplicationJSON);
        fileExtensionToContentTypeMap.put(FileExtensions.PDFExtension, HTTPContentTypes.ApplicationPDF);
        fileExtensionToContentTypeMap.put(FileExtensions.JPGExtension, HTTPContentTypes.ImageJPEG);
        fileExtensionToContentTypeMap.put(FileExtensions.JPEGExtension, HTTPContentTypes.ImageJPEG);
    }

}
