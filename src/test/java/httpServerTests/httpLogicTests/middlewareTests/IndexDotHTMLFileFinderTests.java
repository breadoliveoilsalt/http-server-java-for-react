package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.middleware.IndexDotHTMLFileFinder;
import httpServer.httpLogic.middleware.Middleware;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.*;

public class IndexDotHTMLFileFinderTests {

    private Request request;
    private Response response;
    private IndexDotHTMLFileFinder indexDotHTMLFileFinder;

    @Before
    public void testInit() {
        request = new RequestBuilder().addPath("/").addMethod(HTTPMethods.GET).build();
        response = new Response();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void handleAssignsAnIndexDotHtmlFileToAResponse_sFileFieldIfTheRootPathResourceIsRequested() throws IOException {
        tempFolder.newFile("index.html");
        String pathOfTempFolder = tempFolder.getRoot().getPath();
        indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);

        assertNull(response.file);
        indexDotHTMLFileFinder.handle(request, response);

        assertNotNull(response.file);
        assertEquals("index.html", response.file.getName());
    }

    @Test
    public void handle_sDefaultPathIsTheProjectRootDirectory() {
        indexDotHTMLFileFinder = new IndexDotHTMLFileFinder();

        assertEquals(System.getProperty("user.dir"), indexDotHTMLFileFinder.getPath());
    }

    @Test
    public void handleAddsA200StatusCodeAndOKMessageToResponseIfIndexDotHTMLFileExists() throws IOException {
        tempFolder.newFile("index.html");
        String pathOfTempFolder = tempFolder.getRoot().getPath();
        indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);

        assertNull(response.getStatusCode());
        assertNull(response.getStatusMessage());
        indexDotHTMLFileFinder.handle(request, response);

        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
    }

    @Test
    public void handleDoesNotChangeTheStatusCodeAndOKMessageToResponseIfIndexDotHTMLFileDoesNotExists() {
        String pathOfTempFolder = tempFolder.getRoot().getPath();
        indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);

        assertNull(response.getStatusCode());
        assertNull(response.getStatusMessage());
        indexDotHTMLFileFinder.handle(request, response);

        assertNull(response.getStatusCode());
        assertNull(response.getStatusMessage());
    }

    @Test
    public void handleDoesNotCallHandleOnTheObjectSpecifiedAsTheNextMiddleWareIfTheResourceRequestedIsTheRootPath() {
        NextMiddleware nextMiddleware = createTestMiddleware();
        String pathOfTempFolder = tempFolder.getRoot().getPath();
        indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);
        indexDotHTMLFileFinder.setNext(nextMiddleware);

        indexDotHTMLFileFinder.handle(request, response);

        assertFalse(nextMiddleware.handleWasCalled);
    }

    @Test
    public void handleCallsHandleOnTheObjectSpecifiedAsTheNextMiddleWareIfTheResourceRequestedIsNotTheRootPath() {
        request = new RequestBuilder().addPath("/another_path").addMethod(HTTPMethods.GET).build();
        NextMiddleware nextMiddleware = createTestMiddleware();
        String pathOfTempFolder = tempFolder.getRoot().getPath();
        indexDotHTMLFileFinder = new IndexDotHTMLFileFinder(pathOfTempFolder);
        indexDotHTMLFileFinder.setNext(nextMiddleware);

        indexDotHTMLFileFinder.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

    private NextMiddleware createTestMiddleware() {
        return new NextMiddleware();
    }

    class NextMiddleware extends Middleware {
        public boolean handleWasCalled = false;
        public void handle(Request request, Response response) {
            handleWasCalled = true;
        }
    }
}
