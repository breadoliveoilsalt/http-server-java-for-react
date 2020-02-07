package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.PublicDirectoryFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.views.viewGenerators.ViewGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.*;

public class PublicDirectoryFinderTests {

    private Request request;
    private Response response;
    private PublicDirectoryFinder publicDirectoryFinder;

    @Before
    public void testInit() {
        response = new Response();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void handle_sDefaultPublicRootPathIsAPublicSubDirectoryInTheProjectRootDirectory() {
        publicDirectoryFinder = new PublicDirectoryFinder();

        assertEquals(System.getProperty("user.dir") + "/public", publicDirectoryFinder.getPublicRootPath());
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        request = new RequestBuilder().addPath("/").addMethod(HTTPMethods.GET).build();
        MockMiddleware nextMiddleware = new MockMiddleware();
        publicDirectoryFinder = new PublicDirectoryFinder();
        publicDirectoryFinder.setNext(nextMiddleware);

        publicDirectoryFinder.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

    private void setUpForIndexDotHTMLTests() throws IOException {
        request = new RequestBuilder().addPath("/").addMethod(HTTPMethods.GET).build();
        tempFolder.newFile("index.html");
        String basePath = tempFolder.getRoot().getPath();
        publicDirectoryFinder = new PublicDirectoryFinder(basePath);
    }

    @Test
    public void handleAssignsAnIndexDotHTMLFileToTheResponseIfAnIndexDotHTMLFileIsInThePathOfAGETRequest() throws IOException {
        setUpForIndexDotHTMLTests();

        assertNull(response.file);
        publicDirectoryFinder.handle(request, response);

        assertEquals("index.html", response.file.getName());
    }

    @Test
    public void handleAssignsAnOKStatusCodeToTheResponseIfAnIndexDotHTMLFileIsInThePathOfAGETRequest() throws IOException {
        setUpForIndexDotHTMLTests();

        assertNull(response.statusCode);
        publicDirectoryFinder.handle(request, response);

        assertEquals(HTTPStatusCodes.OK, response.statusCode);
    }

    @Test
    public void handleAssignsAnHTMLContentTypeHeaderToTheResponseIfAnIndexDotHTMLFileIsAssignedToTheResponse() throws IOException {
        setUpForIndexDotHTMLTests();

        assertFalse(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
        publicDirectoryFinder.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
    }

    private void setUpForSubdirectoryTests() throws IOException {
        tempFolder.newFolder("subdirectory");
        request =new RequestBuilder().addPath("/subdirectory").addMethod(HTTPMethods.GET).build();
        String basePath = tempFolder.getRoot().getPath();
        publicDirectoryFinder = new PublicDirectoryFinder(basePath);
    }

    @Test
    public void handleAssignsAnOKStatusCodeToTheResponseIfThePathRequestedMatchesAPublicFolder() throws IOException {
        setUpForSubdirectoryTests();

        assertNull(response.statusCode);
        publicDirectoryFinder.handle(request, response);

        assertEquals(HTTPStatusCodes.OK, response.statusCode);
    }

    @Test
    public void handleAssignsAnHTMLContentTypeHeaderToTheResponseIfThePathRequestedMatchesAPublicFolder() throws IOException {
        setUpForSubdirectoryTests();

        assertFalse(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
        publicDirectoryFinder.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
    }

    @Test
    public void handleCallsOnAViewGeneratorToRenderAStringThatIsAssignedAsAResponse_sStringBodyIfThePathRequestedMatchesAPublicFolder() throws IOException {
        setUpMockViewGeneratorWithViewString("View");

        assertNull(response.stringBody);
        publicDirectoryFinder.handle(request, response);

        assertEquals("View", response.stringBody);
    }

    private void setUpMockViewGeneratorWithViewString(String view) throws IOException {
        class MockViewGenerator implements ViewGenerator {
            public String render() { return view; }
        }

        tempFolder.newFolder("subdirectory");
        request = new RequestBuilder().addPath("/subdirectory").addMethod(HTTPMethods.GET).build();
        String basePath = tempFolder.getRoot().getPath();
        publicDirectoryFinder = new PublicDirectoryFinder(basePath, new MockViewGenerator());
    }

    // Add tests about calling render on a viewer, and then one about the default view
}
