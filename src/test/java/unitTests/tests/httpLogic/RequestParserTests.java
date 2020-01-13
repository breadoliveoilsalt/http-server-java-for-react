package unitTests.tests.httpLogic;

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

        assertTrue(request.getClass() == RequestParser.class);
    }

    @Test
    public void theParsedRequestObjectKnowsTheMethodRequested() {

    }

    @Test
    public void theParsedRequestObjectKnowsThePathRequested() {

    }

    @Test
    public void theParsedRequestObjectKnowsTheHeadersOfTheRequest() {

    }

    @Test
    public void theParsedRequestObjectKnowsTheBodyOfTheRequest() {

    }
}
