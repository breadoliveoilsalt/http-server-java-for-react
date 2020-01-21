package unitTests.tests.httpLogic.handlerTests;

import httpServer.httpLogic.handler.Handler;
import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerTests {

    private Router router;
    private Handler handler;
    private Request clientRequest;
    private Response genericResponse;
    private final String pathWithOnlyGet = "/path_with_only_get";
    private final String GET = "GET";

    @Before
    public void testInit() {
        buildGenericResponse();
        buildRouter();
        initializeHandler();
    }

    private void buildGenericResponse() {
        genericResponse = new ResponseBuilder()
            .addStatusCode("200")
            .addStatusMessage("OK")
            .addHeader("Date", "Some day")
            .addHeader("Content-Length", "Some length")
            .addBody("Some message")
            .build();
    }

    private void buildRouter() {
        RouterBuilder builder = new RouterBuilder();
        builder.createPath(pathWithOnlyGet)
                .addMethodAndAction(GET, () -> returnGenericResponse());
        router = builder.build();
    }

    private Response returnGenericResponse() {
        return genericResponse;
    }

    private void initializeHandler() {
        handler = new Handler(router);
    }

    @Test
    public void handleCausesAnActionSpecifiedByTheRouterToCreateAResponse() throws Exception {
        clientRequest = new RequestBuilder().addPath(pathWithOnlyGet).addMethod(GET).build();

        Response result = handler.handle(clientRequest);

        assertEquals(genericResponse, result);
    }

    @Test
    public void handleReturnsAResponseWithOnlyAPathsMetaDataInResponseToAHEADRequest() throws Exception {
        clientRequest = new RequestBuilder().addPath(pathWithOnlyGet).addMethod("HEAD").build();

        Response result = handler.handle(clientRequest);

        assertEquals("200", result.getStatusCode());
        assertEquals("OK", result.getStatusMessage());
        assertTrue(result.hasHeader("Date", "Some day"));
        assertTrue(result.hasHeader("Content-Length", "Some length"));
        assertNull(result.getBody());
    }

    @Test
    public void handleReturnsA501ResponseWhenTheRouterDoesNotRecognizeTheMethod() throws Exception {
        clientRequest = new RequestBuilder().addPath(pathWithOnlyGet).addMethod("BANANAS").build();

        Response result = handler.handle(clientRequest);

        assertEquals("501", result.getStatusCode());
        assertEquals("Not Implemented", result.getStatusMessage());
        assertEquals("501 Error: Method Not Implemented", result.getBody());
    }

    @Test
    public void handleReturnsA400BadRequestResponseIfARequestIsFlaggedAsInvalid() throws Exception {
        clientRequest = new RequestBuilder().flagAsInvalid().build();

        Response result = handler.handle(clientRequest);

        assertEquals("400", result.getStatusCode());
        assertEquals("Bad Request", result.getStatusMessage());
        assertEquals("400 Error: Bad Request Submitted", result.getBody());
    }

}
