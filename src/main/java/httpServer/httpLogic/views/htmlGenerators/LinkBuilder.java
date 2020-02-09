package httpServer.httpLogic.views.htmlGenerators;

import httpServer.httpLogic.requests.Request;

import java.io.File;

public class LinkBuilder {

    public String buildLinkToParentDirectory(Request request, File file) {
        if (request.getPath().equals("/")) {
            return linkToTopLevelPublicParent();
        } else {
            return linkToNextLevelParent(request, file);
        }
    }

    private String linkToNextLevelParent(Request request, File file) {
        int lengthOfCurrentDirectoryName = ("/" + file.getName()).length();
        int lengthOfCurrentDirectoryPath = request.getPath().length();
        String pathToParent = request.getPath().substring(0, lengthOfCurrentDirectoryPath - lengthOfCurrentDirectoryName);
        if (pathToParent.equals("")) {
            return linkToTopLevelPublicParent();
        } else {
            return "<li> Parent Directory: <a href=\"" +
                    pathToParent +
                    "\">../" +
                    "</a></li>";
        }
    }

    private String linkToTopLevelPublicParent() {
        return "<li> Parent Directory: <a href=\"/\">../</a></li>";
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
        String subdirectoryParentPath = request.getPath();
        if (request.getPath().equals("/")) {
            subdirectoryParentPath = "";
        }
        return "<li> Subdirectory: <a href=\"" +
                subdirectoryParentPath +
                "/" + file.getName() +
                "\">" +
                file.getName() + "/" +
                "</a></li>";
    }

}
