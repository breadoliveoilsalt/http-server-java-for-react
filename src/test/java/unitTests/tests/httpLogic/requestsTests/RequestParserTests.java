package unitTests.tests.httpLogic.requestsTests;

import httpServer.httpLogic.constants.Whitespace;
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

    private String clientRequestStatusLineOnly() {
        return "GET /simple_get HTTP/1.1" + Whitespace.CRLF + Whitespace.CRLF;
    }

    private String clientRequestWithHeadersAndBody() {
        return "GET /simple_get HTTP/1.1" + Whitespace.CRLF +
                "Content-Type: text/html" + Whitespace.CRLF +
                "Content-Length: 1354" + Whitespace.CRLF + Whitespace.CRLF +
                "Request Body";
    }

    @Test
    public void parseReturnsARequestObject() {
        rawClientRequest =  clientRequestStatusLineOnly();

        Object request = requestParser.parse(rawClientRequest);

        assertSame(request.getClass(), Request.class);
    }

    @Test
    public void theParsedRequestObjectKnowsTheMethodRequested() {
        rawClientRequest =  clientRequestStatusLineOnly();

        Request request = requestParser.parse(rawClientRequest);

        assertEquals("GET", request.getMethod());
    }

    @Test
    public void theParsedRequestObjectKnowsThePathRequested() {
        rawClientRequest =  clientRequestStatusLineOnly();

        Request request = requestParser.parse(rawClientRequest);

        assertEquals("/simple_get", request.getPath());
    }

    @Test
    public void theParsedRequestObjectKnowsTheHTTPVersionSpecifiedInTheRequest() {
        rawClientRequest =  clientRequestStatusLineOnly();

        Request request = requestParser.parse(rawClientRequest);

        assertEquals(1.1, request.getHTTPVersion(), 0.002);
    }

    @Test
    public void theParsedRequestObjectKnowsTheHeadersOfTheRequest() {
        rawClientRequest = clientRequestWithHeadersAndBody();

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "text/html");
        expectedHeaders.put("Content-Length", "1354");

        Request request = requestParser.parse(rawClientRequest);

        assertEquals(expectedHeaders, request.getHeaders());
    }

    @Test
    public void theParsedRequestObjectKnowsTheBodyOfTheRequest() {
        rawClientRequest = clientRequestWithHeadersAndBody();

        Request request = requestParser.parse(rawClientRequest);

        assertEquals("Request Body", request.getBody());
    }

    @Test
    public void parseReturnsAResponseMarkedAsUnparsableIfItCannotBeParsed() {
        rawClientRequest = "JibberJabberJibberJabber";

        Request request = requestParser.parse(rawClientRequest);

        assertTrue(request.unparsable());
    }

    @Test
    public void parseReturnsAResponseMarkedAsUnparsableIfTheRequestIsMalformed() {
        rawClientRequest = "HTTP/1.1 /simple_get GET";

        Request request = requestParser.parse(rawClientRequest);

        assertTrue(request.unparsable());
    }

}
