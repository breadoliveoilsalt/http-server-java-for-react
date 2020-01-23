package unitTests.tests.httpLogic.requestsTests;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestBuilderTests {

    private RequestBuilder requestBuilder;

    @Before
    public void testInit() {
        requestBuilder = new RequestBuilder();
    }

    @Test
    public void buildDefaultsToBuildingAValidRequest() {
        Request request = requestBuilder.build();

        assertFalse(request.unparsable());
    }

    @Test
    public void addMethodAddsAMethodToTheBuiltRequest() {
        Request request = requestBuilder.addMethod("POST").build();

        assertEquals("POST", request.getMethod());
    }

    @Test
    public void addPathAddsAPathToTheBuiltRequest() {
        Request request = requestBuilder.addPath("/simple_get").build();

        assertEquals("/simple_get", request.getPath());
    }

    @Test
    public void addHeaderAddsAHeaderToTheBuiltRequest() {
        Request request = requestBuilder.addHeader("Content-Length", "0").build();

        Map<String, String> expectedHeader = new HashMap<>();
        expectedHeader.put("Content-Length", "0");

        assertEquals(expectedHeader, request.getHeaders());
    }

    @Test
    public void addBodyAddsABodyToTheBuiltRequest() {
        Request request = requestBuilder.addBody("Hello World").build();

        assertEquals("Hello World", request.getBody());
    }

    @Test
    public void flagAsInvalidMarksTheBuiltRequestAsInvalid() {
        Request request = requestBuilder.flagAsInvalid().build();

        assertTrue(request.unparsable());
    }

    @Test
    public void methodsCanBeChainedBeforeBuildingARequest() {
        Request request = requestBuilder
                .addMethod("GET")
                .addPath("/simple_get")
                .addHeader("Date", "Today")
                .addHeader("Content-Length", "0")
                .addBody("Hello World")
                .build();

        assertEquals("GET", request.getMethod());
        assertEquals("/simple_get", request.getPath());

        Map<String, String> expectedHeader = new HashMap<>();
        expectedHeader.put("Date", "Today");
        expectedHeader.put("Content-Length", "0");
        assertEquals(expectedHeader, request.getHeaders());

        assertEquals("Hello World", request.getBody());
    }
}
