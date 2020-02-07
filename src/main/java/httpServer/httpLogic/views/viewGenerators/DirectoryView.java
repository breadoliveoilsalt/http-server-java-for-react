package httpServer.httpLogic.views.viewGenerators;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.views.htmlGenerators.LinkBuilder;

import java.io.File;
import java.util.HashSet;

public class DirectoryView implements ViewGenerator {

    File currentRootDirectory;
    Request request;
    StringBuilder stringBuilder;
    HashSet<String> blacklistedFiles;

    public DirectoryView(Request request, File currentRootDirectory) {
        this.request = request;
        this.currentRootDirectory = currentRootDirectory;
        this.stringBuilder = new StringBuilder();
        populateBlacklistedFiles();
    }

    public String render() {
        addInitialHTML();
        addHTMLForParentDirectory();
        addHTMLForFilesInDirectory();
        addClosingHTML();
        return stringBuilder.toString();
    }


    private void addHTMLForParentDirectory() {
        stringBuilder.append(new LinkBuilder().buildLinkToParentDirectory(request, currentRootDirectory));
    }

    private void addHTMLForFilesInDirectory() {
        File[] filesInCurrentRootDirectory = currentRootDirectory.listFiles();
        for (File file : filesInCurrentRootDirectory) {
            if (blacklistedFiles.contains(file.getName())) {
                continue;
            } else if (file.isFile()) {
                stringBuilder.append(new LinkBuilder().buildLinkToFile(request, file));
            } else if (file.isDirectory()) {
                stringBuilder.append(new LinkBuilder().buildLinkToSubdirectory(request, file));
            }
        }
    }

    private void addInitialHTML() {
        stringBuilder.append("<ul>");
    }

    private void addClosingHTML() {
        stringBuilder.append("</ul>");

    }

    private void populateBlacklistedFiles() {
        blacklistedFiles = new HashSet<>();
        blacklistedFiles.add(".DS_Store");
    }
}
