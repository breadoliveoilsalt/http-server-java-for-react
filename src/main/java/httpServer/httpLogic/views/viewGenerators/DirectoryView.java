package httpServer.httpLogic.views.viewGenerators;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.views.htmlGenerators.LinkBuilder;

import java.io.File;
import java.util.HashSet;

public class DirectoryView implements ViewGenerator {

    File currentRootDirectory;
    Request request;
    StringBuilder htmlBuilder;
    HashSet<String> blacklistedFiles;

    public DirectoryView(Request request, File currentRootDirectory) {
        this.request = request;
        this.currentRootDirectory = currentRootDirectory;
        this.htmlBuilder = new StringBuilder();
        populateBlacklistedFiles();
    }

    // Why am I not writing to a file?
    // update to return a file
    public String render() {
        addInitialHTML();
        addHTMLForFilesInDirectory();
        addClosingHTML();
        // FIX:
//        return filesInCurrentRootDirectory[0];
        return "hey";
    }

    private void addHTMLForFilesInDirectory() {
        File[] filesInCurrentRootDirectory = currentRootDirectory.listFiles();
        for (File file : filesInCurrentRootDirectory) {
            if (blacklistedFiles.contains(file.getName())) {
                continue;
            } else if (file.isFile()) {
                htmlBuilder.append(new LinkBuilder().buildLinkToFile(request, file));
            } else if (file.isDirectory()) {
                htmlBuilder.append(new LinkBuilder().buildLinkToDirectory(request, file));
            }
        }
    }

    private void addInitialHTML() {
        htmlBuilder.append("<ul>");
    }

    private void addClosingHTML() {
        htmlBuilder.append("</ul>");

    }

    private void populateBlacklistedFiles() {
        blacklistedFiles = new HashSet<>();
        blacklistedFiles.add(".DS_Store");
    }
}
