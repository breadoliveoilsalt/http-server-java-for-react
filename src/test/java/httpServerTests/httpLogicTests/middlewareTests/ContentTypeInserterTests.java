package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.ContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.middleware.ContentTypeInserter;
import httpServer.httpLogic.middleware.FileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

public class ContentTypeInserterTests {

    private Request request;
    private Response response;
    private ContentTypeInserter contentTypeInserter;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void testInit() {
        request = new RequestBuilder().build();
        response = new Response();
        contentTypeInserter = new ContentTypeInserter();
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        MockMiddleware nextMiddleware = new MockMiddleware();
        contentTypeInserter.setNext(nextMiddleware);

        contentTypeInserter.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

    @Test
    public void handleAddsTextPlainContentTypeIfThereIsAStringBody() {
        response.stringBody = "Some text";

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, ContentTypes.TextPlain));
    }

    // Add one for txt

//        tempFolder.newFile("index.html");
//        String basePath = tempFolder.getRoot().getPath();
//
//        assertNull(response.file);
//        fileFinder = new FileFinder(basePath);
//        fileFinder.handle(request, response);

}
