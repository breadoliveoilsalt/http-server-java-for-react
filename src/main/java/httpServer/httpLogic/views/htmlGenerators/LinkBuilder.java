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

    public String buildLinkToDirectory(Request request, File file) {
        return "<li> Directory: <a href=\"" +
                request.getPath() +
                "/" + file.getName() +
                "\">" +
                file.getName() +
                "</a></li>";
    }

}
