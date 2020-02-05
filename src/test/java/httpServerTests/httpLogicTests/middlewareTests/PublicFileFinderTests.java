package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.PublicFileFinder;
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
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PublicFileFinderTests {

    private Request request;
    private Response response;
    private PublicFileFinder publicFileFinder;

    @Before
    public void testInit() {
        request = new RequestBuilder().addPath("/index.html").addMethod(HTTPMethods.GET).build();
        response = new Response();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void handleAssignsAFileToAResponse_sFileFieldIfAPathMatchesAFileWithAGetRequest() throws IOException {
        tempFolder.newFile("index.html");
        String basePath = tempFolder.getRoot().getPath();

        assertNull(response.file);
        publicFileFinder = new PublicFileFinder(basePath);
        publicFileFinder.handle(request, response);

        assertEquals("index.html", response.file.getName());
    }

    @Test
    public void handleCanFindANestedFile() throws IOException {
        File testSubDirectory = tempFolder.newFolder("testSubDirectory");
        Path pathToTempIndexFile = Paths.get(testSubDirectory.getPath() + "/index.html");
        Files.createFile(pathToTempIndexFile);
        request = new RequestBuilder().addPath("/testSubDirectory/index.html").addMethod(HTTPMethods.GET).build();

        assertNull(response.file);
        String basePath = tempFolder.getRoot().getPath();
        publicFileFinder = new PublicFileFinder(basePath);
        publicFileFinder.handle(request, response);

        assertEquals("index.html", response.file.getName());
    }

    @Test
    public void handleDoesNotAssignAFileToAResponse_sFileFieldIfTheRequestIsNotAGETRequest() throws IOException {
        request = new RequestBuilder().addPath("/index.html").addMethod(HTTPMethods.POST).build();
        tempFolder.newFile("index.html");
        String basePath = tempFolder.getRoot().getPath();

        assertNull(response.file);
        publicFileFinder = new PublicFileFinder(basePath);
        publicFileFinder.handle(request, response);

        assertNull(response.file);
    }

    @Test
    public void handle_sDefaultBasePathIsAPublicSubDirectoryInTheProjectRootDirectory() {
        publicFileFinder = new PublicFileFinder();

        assertEquals(System.getProperty("user.dir") + "/public", publicFileFinder.getPublicFilesPath());
    }

    @Test
    public void handleAddsA200StatusCodeToResponseIfAFileCorrespondingToThePathExists() throws IOException {
        tempFolder.newFile("index.html");
        String basePath = tempFolder.getRoot().getPath();

        assertNull(response.getStatusCode());
        publicFileFinder = new PublicFileFinder(basePath);
        publicFileFinder.handle(request, response);

        assertEquals(HTTPStatusCodes.OK, response.statusCode);
    }

    @Test
    public void handleDoesNotChangeTheStatusCodeIfTheFileDoesNotExists() {
        request = new RequestBuilder().addPath("/index.html").addMethod(HTTPMethods.GET).build();
        String basePath = tempFolder.getRoot().getPath();

        assertNull(response.statusCode);
        publicFileFinder = new PublicFileFinder(basePath);
        publicFileFinder.handle(request, response);

        assertNull(response.statusCode);
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        MockMiddleware nextMiddleware = new MockMiddleware();
        publicFileFinder = new PublicFileFinder();
        publicFileFinder.setNext(nextMiddleware);

        publicFileFinder.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }
}
