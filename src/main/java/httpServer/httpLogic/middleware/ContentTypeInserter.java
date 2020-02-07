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
    private Map<String, String> fileExtensionToContentTypeMap;

    public ContentTypeInserter() {
        populateFileExtensionToContentTypeMap();
    }

    @Override
    public void handle(Request request, Response response) {
        this.response = response;
        if (responseHasOKStatusCode()) {
            examineStringBodyOrFileForContentType();
        }
        passToNextMiddleware(request, response);
    }

    private boolean responseHasOKStatusCode() {
        return response.statusCode.equals(HTTPStatusCodes.OK);
    }

    private void examineStringBodyOrFileForContentType() {
        if (contentTypeNeedsIdentification()) {
            assignDefaultContentType();
            checkForFileToUpdateContentType();
        }
    }

    private boolean contentTypeNeedsIdentification() {
        return !response.hasHeaderValue(HTTPHeaders.ContentType) &&
                (response.stringBody != null || response.file != null);
    }

    private void assignDefaultContentType() {
        addContentTypeHeader(HTTPContentTypes.TextPlain);
    }

    private void checkForFileToUpdateContentType() {
        if (response.file != null) {
            try {
                assignContentTypeBasedOnFileExtension();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void assignContentTypeBasedOnFileExtension() {
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
        fileExtensionToContentTypeMap = new HashMap<>();
        fileExtensionToContentTypeMap.put(FileExtensions.TextExtension, HTTPContentTypes.TextPlain);
        fileExtensionToContentTypeMap.put(FileExtensions.HTMLExtension, HTTPContentTypes.TextHTML);
        fileExtensionToContentTypeMap.put(FileExtensions.HTMExtension, HTTPContentTypes.TextHTML);
        fileExtensionToContentTypeMap.put(FileExtensions.CSSExtension, HTTPContentTypes.TextCSS);
        fileExtensionToContentTypeMap.put(FileExtensions.JSExtension, HTTPContentTypes.TextJavaScript);
        fileExtensionToContentTypeMap.put(FileExtensions.JSONExtension, HTTPContentTypes.ApplicationJSON);
        fileExtensionToContentTypeMap.put(FileExtensions.PDFExtension, HTTPContentTypes.ApplicationPDF);
        fileExtensionToContentTypeMap.put(FileExtensions.JPGExtension, HTTPContentTypes.ImageJPEG);
        fileExtensionToContentTypeMap.put(FileExtensions.JPEGExtension, HTTPContentTypes.ImageJPEG);
        fileExtensionToContentTypeMap.put(FileExtensions.PNGExtension, HTTPContentTypes.ImagePNG);
        fileExtensionToContentTypeMap.put(FileExtensions.GIFExtension, HTTPContentTypes.ImageGIF);
        fileExtensionToContentTypeMap.put(FileExtensions.WAVExtension, HTTPContentTypes.AudioWAV);
        fileExtensionToContentTypeMap.put(FileExtensions.MP3Extension, HTTPContentTypes.AudioMPEG);
        fileExtensionToContentTypeMap.put(FileExtensions.MP4Extension, HTTPContentTypes.VideoMP4);
        fileExtensionToContentTypeMap.put(FileExtensions.MOVExtension, HTTPContentTypes.VideoQuickTime);
    }

}
