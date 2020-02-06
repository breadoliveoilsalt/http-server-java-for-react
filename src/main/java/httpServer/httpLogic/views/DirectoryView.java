package httpServer.httpLogic.views;

import java.io.File;
import java.util.HashSet;

public class DirectoryView implements HTMLFileGenerator {

    File currentRootDirectory;
    StringBuilder htmlBuilder;
    HashSet<String> blacklistedFiles;

    public DirectoryView(File currentRootDirectory) {
        this.currentRootDirectory = currentRootDirectory;
        this.htmlBuilder = new StringBuilder();
        populateBlacklistedFiles();
    }

    public File generateHTMLFile() {
        File[] filesInCurrentRootDirectory = currentRootDirectory.listFiles();
        for (File file : filesInCurrentRootDirectory) {
            if (blacklistedFiles.contains(file.getName())) {
                continue;
            } else if (file.isFile()) {
                htmlBuilder.append(new LinkBuilder().buildFileLink(file.getName()));
            }
        }

    }

    private void populateBlacklistedFiles() {
        blacklistedFiles = new HashSet<>();
        blacklistedFiles.add(".DS_Store");
    }
}
