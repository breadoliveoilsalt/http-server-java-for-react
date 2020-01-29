package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.middleware.IndexDotHTMLFileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexDotHTMLFileFinderTests {

    @Test
    public void handleAssignsAnIndexDotHtmlFileInTheRootDirectoryToAResponse_sFileField() {
        Request request = new RequestBuilder().addPath("/").addMethod(HTTPMethods.GET).build();
        Response response = new Response();
        assertNull(response.file);

        IndexDotHTMLFileFinder indexDotHTMLFileFinder = new IndexDotHTMLFileFinder();

        indexDotHTMLFileFinder.handle(request, response);

        assertNotNull(response.file);
        assertEquals("index.html", response.file.getName());
    }


}
