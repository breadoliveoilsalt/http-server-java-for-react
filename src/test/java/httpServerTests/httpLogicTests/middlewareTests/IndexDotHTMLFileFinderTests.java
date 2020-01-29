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
    public void handleAssignsAnIndexDotHtmlFileToAResponse_sFileFieldIfTheRootPathResourceIsRequested() throws IOException {
        Request request = new RequestBuilder().addPath("/").addMethod(HTTPMethods.GET).build();
        Response response = new Response();
        tempFolder.newFile("index.html");
        String pathOfTempFolder = tempFolder.getRoot().getPath();
        IndexDotHTMLFileFinder indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);

        assertNull(response.file);
        indexDotHTMLFileFinder.handle(request, response);

        assertNotNull(response.file);
        assertEquals("index.html", response.file.getName());
    }

    @Test
    public void handle_sDefaultPathIsTheProjectRootDirectory() {
        IndexDotHTMLFileFinder indexDotHTMLFileFinder = new IndexDotHTMLFileFinder();

        assertEquals(System.getProperty("user.dir"), indexDotHTMLFileFinder.getPath());
    }


}
