package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.middleware.ContentLengthInserter;
import httpServer.httpLogic.middleware.FileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class ContentLengthInserterTests {

    private Request request;
    private Response response;
    private FileFinder fileFinder;


    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void testInit() {
        request = new RequestBuilder().addPath("/index.html").addMethod(HTTPMethods.GET).build();
        response = new Response();
    }

    @Test
    public void handleAddsAHeaderWithTheContentLengthOfAResonse_sFile() throws IOException {
        File responseFile = tempFolder.newFile("index.html");
        String responseFileText = "0123456789";
        Files.write(Paths.get(responseFile.getPath()), responseFileText.getBytes());
        response.file = responseFile;

        new ContentLengthInserter().handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentLength, "10"));
    }

    @Test
    public void handleAddsAHeaderWithTheContentLengthOfAResponse_sStringBody() {
        response.stringBody = "0123456789";

        new ContentLengthInserter().handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentLength, "10"));
    }

    @Test
    public void handleAddsAHeaderWithTheContentLengthOfZeroIfThereIsNoFileOrStringBodyToTheResponse() {
        new ContentLengthInserter().handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentLength, "0"));
    }
}
