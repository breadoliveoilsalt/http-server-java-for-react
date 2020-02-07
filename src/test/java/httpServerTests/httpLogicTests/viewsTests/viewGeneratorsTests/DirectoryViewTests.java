package httpServerTests.httpLogicTests.viewsTests.viewGeneratorsTests;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.views.viewGenerators.DirectoryView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DirectoryViewTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void renderGeneratesAStringWithAnUnorderedList() throws IOException {
        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).addPath(tempFolder.getRoot().getPath()).build();
        File directoryFile = tempFolder.newFolder("tempDirectory");

        String result = new DirectoryView(request, directoryFile).render();

        String expectedResult =
                "<ul></ul>";

        assertEquals(expectedResult, result);
    }

    @Test
    public void render_sUnorderedListListsFilesInTheDirectoryFilePassedIn() throws IOException {
        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).addPath(tempFolder.getRoot().getPath()).build();
        File directoryFile = tempFolder.newFolder("tempDirectory");
        createTempFilesFor(directoryFile);
//        File directoryFile = tempFolder.newFolder("tempDirectory");
//        Files.createFile(Paths.get(directoryFile.getPath() + "/doc.pdf"));
//        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).addPath("/sampleDirectoryForTests").build();
//        ClassLoader classLoader = getClass().getClassLoader();
//        String currentPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//        File directoryFileToTest = new File(currentPath + "/sampleDirectoryForTests");
////        String currentTestFilePath = Paths.get("").toAbsolutePath().toString();
////        File directoryFile = new File(currentTestFilePath + "/sampleDirectoryForTests");
////        File directoryFile = tempFolder.newFolder("tempDirectory");
////        Path pathToNewPDFFile = Paths.get(tempFolder.getRoot().getPath() + "/doc.pdf");
////        Files.createFile(pathToNewPDFFile);
////
        String result = new DirectoryView(request, directoryFile).render();

        String expectedResult =
                "<ul></ul>";

        assertEquals(expectedResult, result);

    }

    private void createTempFilesFor(File directoryFile) throws IOException {
        Files.createFile(Paths.get(directoryFile.getPath() + "/doc.pdf"));
        Files.createFile(Paths.get(directoryFile.getPath() + "/page.html"));
        Files.createDirectory(Paths.get(directoryFile.getPath() + "/subDirectory"));
    }
}
