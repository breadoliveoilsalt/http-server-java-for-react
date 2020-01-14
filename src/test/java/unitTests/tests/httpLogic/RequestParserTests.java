package unitTests.tests.httpLogic;

import httpServer.httpLogic.Request;
import httpServer.httpLogic.RequestParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestParserTests {

    private String rawClientRequest;

    @Before
    public void testInit() {
        rawClientRequest = "";
    }

    @Test
    public void parseReturnsARequestObject() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Object request = RequestParser.parse(rawClientRequest);

        assertTrue(request.getClass() == Request.class);
    }

    @Test
    public void theParsedRequestObjectKnowsTheMethodRequested() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Request request = RequestParser.parse(rawClientRequest);

        assertEquals("GET", request.getMethod());
    }

    @Test
    public void theParsedRequestObjectKnowsThePathRequested() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Request request = RequestParser.parse(rawClientRequest);

        assertEquals("/simple_get", request.getPath());
    }

    @Test
    public void theParsedRequestObjectKnowsTheHTTPVersionSpecifiedInTheRequest() {
        rawClientRequest = "GET /simple_get HTTP/1.1";

        Request request = RequestParser.parse(rawClientRequest);

        assertEquals("HTTP/1.1", request.getHTTPVersion());
    }

    @Test
    public void theParsedRequestObjectKnowsTheHeadersOfTheRequest() {
        rawClientRequest =  "GET /simple_get HTTP/1.1\n" +
                            "Content-Type: text/html\n" +
                            "Content-Length: 1354";

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type:", "text/html");
        expectedHeaders.put("Content-Length:", "1354");

        Request request = RequestParser.parse(rawClientRequest);

        assertEquals(expectedHeaders, request.getHeaders());
    }
//
//    @Test
//    public void theParsedRequestObjectKnowsTheBodyOfTheRequest() {
//
//    }
}
