package httpServerTests.httpLogicTests.viewsTests.viewGeneratorsTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.views.htmlGenerators.DirectoryLinkRenderer;
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
import static org.junit.Assert.assertTrue;

public class DirectoryViewTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void renderGeneratesAStringWithAnUnorderedList() throws IOException {
        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).addPath(tempFolder.getRoot().getPath()).build();
        File directoryFile = tempFolder.newFolder("tempDirectory");

        String result = new DirectoryView(request, directoryFile).render();

        assertTrue(result.contains("<ul>"));
        assertTrue(result.contains("</ul>"));
    }

    @Test
    public void renderUsesAppropriateMethodsOfDirectoryLinkRendererToBuildTheStringRepresentingTheDirectoryPassedIn() throws IOException {
        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).addPath("/tempParentDirectory").build();
        File parentDirectory = tempFolder.newFolder("tempParentDirectory");
        Path pdfFile = Files.createFile(Paths.get(parentDirectory.getPath() + "/doc.pdf"));
        Path htmlFile = Files.createFile(Paths.get(parentDirectory.getPath() + "/page.html"));
        Path subDirectory = Files.createDirectory(Paths.get(parentDirectory.getPath() + "/subDirectory"));

        String result = new DirectoryView(request, parentDirectory).render();

        String expectedResult =
                new DirectoryLinkRenderer().openUnorderedList() +
                new DirectoryLinkRenderer().buildLinkToParentDirectory(request, parentDirectory) +
                new DirectoryLinkRenderer().buildLinkToFile(request, new File(htmlFile.toString())) +
                new DirectoryLinkRenderer().buildLinkToSubdirectory(request, new File(subDirectory.toString())) +
                new DirectoryLinkRenderer().buildLinkToFile(request, new File(pdfFile.toString())) +
                new DirectoryLinkRenderer().closeUnorderedList();

        System.out.println(expectedResult);
        System.out.println(result);
        assertEquals(expectedResult, result);
    }

}
