package unitTests.tests.httpLogic.responsesTests;

import httpServer.httpLogic.Constants;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.responses.ResponseParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseParserTests {

    Response responseObject;
    ResponseParser responseParser;

    @Before
    public void testInit() {
        responseParser = new ResponseParser();
    }

    @Test
    public void stringifyTakesDataFromAResponseObjectAndTurnsItIntoAStringWithTwoCRLFSMarkingTheEndOfTheMetaData() {
        buildResponseForBasicStringTest();

        String result = responseParser.stringify(responseObject);

        String expectedResult = "HTTP/1.1 200 OK" + Constants.CRLF + Constants.CRLF;
        assertEquals(expectedResult, result);
   }

   private void buildResponseForBasicStringTest() {
        responseObject = new Response(
               "HTTP/1.1",
               "200",
               "OK",
               null,
               null);
   }

    @Test
    public void stringifyCanStringifyAResponseObjectsHeadersWithCRLFFollowingEachIntermediaryHeader() {
        String header1Key = "Content-Length";
        String header1Value = "0";
        String header2Key = "Date";
        String header2Value = "Some Date";
        buildResponseForHeadersTest(header1Key, header1Value, header2Key, header2Value);

        String result = responseParser.stringify(responseObject);

        String expectedResult =
                "HTTP/1.1 200 OK" + Constants.CRLF +
                "Content-Length: 0" + Constants.CRLF +
                "Date: Some Date" + Constants.CRLF +
                Constants.CRLF;
        assertEquals(expectedResult, result);
    }

    private void buildResponseForHeadersTest(String header1Key, String header1Value, String header2Key, String header2Value ) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
            .addHeader(header1Key, header1Value)
            .addHeader(header2Key, header2Value);

        responseObject = builder.build();
    }

    @Test public void stringifyAddsAMessageBodyIfTheResponseObjectHasABody() {
        String header1Key = "Content-Length";
        String header1Value = "5";
        String body = "Hello";
        buildResponseForBodyTest(header1Key, header1Value, body);

        String result = responseParser.stringify(responseObject);

        String expectedResult =
                "HTTP/1.1 200 OK" + Constants.CRLF +
                "Content-Length: 5" + Constants.CRLF +
                Constants.CRLF +
                "Hello";
        assertEquals(expectedResult, result);
    }

    private void buildResponseForBodyTest(String header1Key, String header1Value, String body) {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
                .addHeader(header1Key, header1Value)
                .addBody(body);

        responseObject = builder.build();
    }

}
