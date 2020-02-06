package httpServerTests.httpLogicTests.viewsTests.htmlGeneratorsTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.views.htmlGenerators.LinkBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LinkBuilderTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void buildLinkToFileReturnsAnHTMLStringWithAnATag() throws IOException {
        Request request = new RequestBuilder().addPath("/docs").addMethod(HTTPMethods.GET).build();
        File file = tempFolder.newFile("doc.pdf");

        String result = new LinkBuilder().buildLinkToFile(request, file);

        String expectedResult =
            "<li> File: <a src=\"" +
            request.getPath() +
            "/" + file.getName() +
            "\" >" +
            file.getName() +
            "</a> </li>";

        assertEquals(expectedResult, result);
    }

    @Test
    public void buildLinkToDirectoryReturnsAStringWithAnATag() throws IOException {
        Request request = new RequestBuilder().addPath("/docs").addMethod(HTTPMethods.GET).build();
        File file = tempFolder.newFile("doc.pdf");

        String result = new LinkBuilder().buildLinkToFile(request, file);

        String expectedResult =
            "<li> Directory: <a src=\"" +
            request.getPath() +
            "/" + file.getName() +
            "\" >" +
            file.getName() +
            "</a> </li>";

        assertEquals(expectedResult, result);
    }

}
