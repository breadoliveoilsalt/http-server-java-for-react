package httpServer.httpLogic.views.htmlGenerators;

import httpServer.httpLogic.requests.Request;

import java.io.File;

public class LinkBuilder {

    public String buildLinkToFile(Request request, File file) {
        return "<li> File: <a href=\"" +
                request.getPath() +
                "/" + file.getName() +
                "\">" +
                file.getName() +
                "</a></li>";
    }

    public String buildLinkToSubdirectory(Request request, File file) {
        return "<li> Subdirectory: <a href=\"" +
                request.getPath() +
                "/" + file.getName() +
                "\">" +
                file.getName() +
                "</a></li>";
    }

}
