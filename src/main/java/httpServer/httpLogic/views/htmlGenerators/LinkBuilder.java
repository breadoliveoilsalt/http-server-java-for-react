package httpServer.httpLogic.views.htmlGenerators;

import httpServer.httpLogic.requests.Request;

import java.io.File;

public class LinkBuilder {

    public String buildLinkToParentDirectory(Request request, File file) {
        int lengthOfCurrentDirectoryName = ("/" + file.getName()).length();
        int lengthOfCurrentDirectoryPath = request.getPath().length();
        String pathToParent = request.getPath().substring(0, lengthOfCurrentDirectoryPath - lengthOfCurrentDirectoryName);
        if (pathToParent.equals("")) {
            pathToParent = "/";
        }

        return "<li> Parent Directory: <a href=\"" +
                pathToParent +
                "\">../" +
                "</a></li>";
    }

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
                file.getName() + "/" +
                "</a></li>";
    }

}
