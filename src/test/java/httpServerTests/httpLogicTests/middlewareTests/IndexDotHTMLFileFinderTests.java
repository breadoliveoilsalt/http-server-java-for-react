package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.middleware.IndexDotHTMLFileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class IndexDotHTMLFileFinderTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void handleAssignsAnIndexDotHtmlFileToAResponse_sFileField() throws IOException {
        File tempIndexDotHtmlFile = tempFolder.newFile("index.html");
        Request request = new RequestBuilder().addPath("/").addMethod(HTTPMethods.GET).build();
        Response response = new Response();
        assertNull(response.file);

        String pathOfTempFolder = tempFolder.getRoot().getPath();

        IndexDotHTMLFileFinder indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);

        indexDotHTMLFileFinder.handle(request, response);

        assertNotNull(response.file);
        assertEquals("index.html", response.file.getName());
    }


}
