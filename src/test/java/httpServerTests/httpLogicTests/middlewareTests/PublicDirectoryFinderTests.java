package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.middleware.PublicFileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PublicDirectoryFinderTests {

    private Request request;
    private Response response;
    private PublicDirectoryFinder publicDirectoryFinder;

    @Before
    public void testInit() {
//        request = new RequestBuilder().addPath("/index.html").addMethod(HTTPMethods.GET).build();
        response = new Response();
        publicDirectoryFinder = new PublicDirectoryFinder();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void handleAssignsAnIndexDotHTMLFileToTheResponseIfAnIndexDotHTMLFileIsInThePathOfAGETRequest() throws IOException {
        request = new RequestBuilder().addPath("/index.html").addMethod(HTTPMethods.GET).build();

        tempFolder.newFile("index.html");
        String basePath = tempFolder.getRoot().getPath();

        assertNull(response.file);
        publicDirectoryFinder.handle(request, response);

        assertEquals("index.html", response.file.getName());
    }


}
