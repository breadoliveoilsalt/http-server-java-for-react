package httpServer.httpLogic.views.viewGenerators;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.views.htmlGenerators.DirectoryLinkRenderer;

import java.io.File;
import java.util.HashSet;

public class DirectoryView implements ViewGenerator {

    private final File currentRootDirectory;
    private final Request request;
    private final StringBuilder stringBuilder;
    private final DirectoryLinkRenderer renderer;
    HashSet<String> blacklistedFiles;

    public DirectoryView(Request request, File currentRootDirectory) {
        this.request = request;
        this.currentRootDirectory = currentRootDirectory;
        this.stringBuilder = new StringBuilder();
        this.renderer = new DirectoryLinkRenderer();
        populateBlacklistedFiles();
    }

    public String render() {
        addInitialHTML();
        addHTMLForParentDirectory();
        addHTMLForFilesInDirectory();
        addClosingHTML();
        return stringBuilder.toString();
    }


    private void addInitialHTML() {
        stringBuilder.append(renderer.openUnorderedList());
    }

    private void addHTMLForParentDirectory() {
        stringBuilder.append(new DirectoryLinkRenderer().buildLinkToParentDirectory(request, currentRootDirectory));
    }

    private void addHTMLForFilesInDirectory() {
        File[] filesInCurrentRootDirectory = currentRootDirectory.listFiles();
        for (File file : filesInCurrentRootDirectory) {
            if (blacklistedFiles.contains(file.getName())) {
                continue;
            } else if (file.isFile()) {
                stringBuilder.append(new DirectoryLinkRenderer().buildLinkToFile(request, file));
            } else if (file.isDirectory()) {
                stringBuilder.append(new DirectoryLinkRenderer().buildLinkToSubdirectory(request, file));
            }
        }
    }

    private void addClosingHTML() {
        stringBuilder.append(renderer.closeUnorderedList());
    }

    private void populateBlacklistedFiles() {
        blacklistedFiles = new HashSet<>();
        blacklistedFiles.add(".DS_Store");
    }
}
