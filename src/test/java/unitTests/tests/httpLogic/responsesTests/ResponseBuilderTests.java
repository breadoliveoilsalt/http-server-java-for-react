package unitTests.tests.httpLogic.responsesTests;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ResponseBuilderTests {

    private ResponseBuilder responseBuilder;

    @Before
    public void testInit() {
        responseBuilder = new ResponseBuilder();
    }

    @Test
    public void buildAddsADefaultHTTPVersionToAResponse() {
        Response response = responseBuilder.build();

        assertEquals("HTTP/1.1", response.getHttpVersion());
    }

    @Test
    public void addStatusCodeAddsAStatusCodeToABuiltResponse() {
        responseBuilder.addStatusCode("200");

        Response response = responseBuilder.build();

        assertEquals("200", response.getStatusCode());
    }

    @Test
    public void addStatusMessageAddsAStatusMessageToABuiltResponse() {
        responseBuilder.addStatusMessage("OK");

        Response response = responseBuilder.build();

        assertEquals("OK", response.getStatusMessage());
    }

    @Test public void addOKStatusLineAddsADefaultStatusLineToABuiltResponse() {
        responseBuilder.addOKStatusLine();

        Response response = responseBuilder.build();

        assertEquals("HTTP/1.1", response.getHttpVersion());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());
    }

    @Test public void addHeaderAddsAHeaderToABuiltResponseAsAKeyValuePair() {
        responseBuilder.addHeader("Content-Length", "0");

        Response response = responseBuilder.build();

        HashMap<String, String> expectedResult = new HashMap<>();
        expectedResult.put("Content-Length", "0");

        assertEquals(expectedResult, response.getHeaders());
    }

    @Test public void addBodyAddsABodyToABuiltResponse() {
        responseBuilder.addBody("A Great Message");

        Response response = responseBuilder.build();

        assertEquals("A Great Message", response.getBody());
    }

    @Test public void methodsCanBeChained() {
        responseBuilder.addOKStatusLine()
            .addHeader("Content-Length", "0")
            .addHeader("Date", "Today")
            .addBody("A Great Message");

        Response response = responseBuilder.build();

        assertEquals("HTTP/1.1", response.getHttpVersion());
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getStatusMessage());

        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Length", "0");
        expectedHeaders.put("Date", "Today");

        assertEquals(expectedHeaders, response.getHeaders());

        assertEquals("A Great Message", response.getBody());

    }

}
