package unitTests.tests.httpLogic;

import httpServer.httpLogic.Request;
import httpServer.httpLogic.RequestParser;
import org.junit.Before;
import org.junit.Test;

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

//    @Test
//    public void theParsedRequestObjectKnowsTheMethodRequested() {
//
//    }
//
//    @Test
//    public void theParsedRequestObjectKnowsThePathRequested() {
//
//    }
//
//    @Test
//    public void theParsedRequestObjectKnowsTheHeadersOfTheRequest() {
//
//    }
//
//    @Test
//    public void theParsedRequestObjectKnowsTheBodyOfTheRequest() {
//
//    }
}
