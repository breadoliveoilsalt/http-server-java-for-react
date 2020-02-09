package httpServerTests.httpLogicTests.viewsTests.htmlGeneratorsTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.views.htmlGenerators.DirectoryLinkRenderer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryLinkRendererTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void buildLinkToParentDirectoryReturnsAnHTMLStringWithAnATagLinkingToTheParentDirectoryOfTheFilePassedIn() throws IOException {
        Request request = new RequestBuilder().addPath("/docs").addMethod(HTTPMethods.GET).build();
        File docsDirectory = tempFolder.newFile("docs");

        String result = new DirectoryLinkRenderer().buildLinkToParentDirectory(request, docsDirectory);

        String expectedResult =
                "<li> Parent Directory: <a href=\"/\">../</a></li>";

        assertEquals(expectedResult, result);
    }

    @Test
    public void buildLinkToParentDirectoryWorksForNestedPaths() throws IOException {
        Request request = new RequestBuilder().addPath("/docs/sample_project").addMethod(HTTPMethods.GET).build();
        File docsDirectory = tempFolder.newFile("docs");
        File projectDirectory = new File(docsDirectory.getPath() + "/sample_project");
        projectDirectory.mkdir();
        String result = new DirectoryLinkRenderer().buildLinkToParentDirectory(request, projectDirectory);

        String expectedResult =
                "<li> Parent Directory: <a href=\"/docs\">../</a></li>";

        assertEquals(expectedResult, result);
    }

    @Test
    public void buildLinkToFileReturnsAnHTMLStringWithAnATagLinkingToTheFile() throws IOException {
        Request request = new RequestBuilder().addPath("/docs").addMethod(HTTPMethods.GET).build();
        File file = tempFolder.newFile("doc.pdf");

        String result = new DirectoryLinkRenderer().buildLinkToFile(request, file);

        String expectedResult =
            "<li> File: <a href=\"" +
            request.getPath() +
            "/" + file.getName() +
            "\">" +
            file.getName() +
            "</a></li>";

        assertEquals(expectedResult, result);
    }

    @Test
    public void buildLinkToSubdirectoryReturnsAStringWithAnATagLinkingToTheSubdirectory() throws IOException {
        Request request = new RequestBuilder().addPath("/docs").addMethod(HTTPMethods.GET).build();
        File file = tempFolder.newFile("doc.pdf");

        String result = new DirectoryLinkRenderer().buildLinkToSubdirectory(request, file);

        String expectedResult =
            "<li> Subdirectory: <a href=\"" +
            request.getPath() +
            "/" + file.getName() +
            "\">" +
            file.getName() + "/" +
            "</a></li>";

        assertEquals(expectedResult, result);
    }

}
