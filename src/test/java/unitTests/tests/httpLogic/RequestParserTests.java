package unitTests.tests.httpLogic;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RequestParserTests {

    private String rawClientRequest;
    private RequestParser requestParser;

    @Before
    public void testInit() {
        requestParser = new RequestParser();
    }

    @Test
    public void parseReturnsARequestObject() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Object request = requestParser.parse(rawClientRequest);

        assertTrue(request.getClass() == Request.class);
    }

    @Test
    public void theParsedRequestObjectKnowsTheMethodRequested() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Request request = requestParser.parse(rawClientRequest);

        assertEquals("GET", request.getMethod());
    }

    @Test
    public void theParsedRequestObjectKnowsThePathRequested() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Request request = requestParser.parse(rawClientRequest);

        assertEquals("/simple_get", request.getPath());
    }

    @Test
    public void theParsedRequestObjectKnowsTheHTTPVersionSpecifiedInTheRequest() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Request request = requestParser.parse(rawClientRequest);

        assertEquals(1.1, request.getHTTPVersion(), 0.002);
    }

    @Test
    public void theParsedRequestObjectKnowsTheHeadersOfTheRequest() {
        rawClientRequest =  "GET /simple_get HTTP/1.1\n" +
                            "Content-Type: text/html\n" +
                            "Content-Length: 1354";

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "text/html");
        expectedHeaders.put("Content-Length", "1354");

        Request request = requestParser.parse(rawClientRequest);

        assertEquals(expectedHeaders, request.getHeaders());
    }

    @Test
    public void theParsedRequestObjectKnowsTheBodyOfTheRequest() {
        rawClientRequest =  "GET /simple_get HTTP/1.1\n" +
                            "Content-Type: text/html\n" +
                            "Content-Length: 1354" +
                            "\r\n" +
                            "Request Body";

        Request request = requestParser.parse(rawClientRequest);

        assertEquals("Request Body", request.getBody());
    }

}
