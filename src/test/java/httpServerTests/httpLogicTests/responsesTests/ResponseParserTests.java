package httpServerTests.httpLogicTests.responsesTests;

import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.responses.ResponseParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ResponseParserTests {

    Response responseObject;
    ResponseParser responseParser;

    @Before
    public void testInit() {
        responseParser = new ResponseParser();
    }

    @Test
    public void convertToByteArrayOutputStreamConvertsAResponseObjectToAByteArrayOutputStream() throws IOException {
        buildResponseForBasicStringTest();
        String rawResponseString = "HTTP/1.1 200 OK" + Whitespace.CRLF + Whitespace.CRLF;
        ByteArrayOutputStream expectedResult = new ByteArrayOutputStream();
        expectedResult.write(rawResponseString.getBytes());

        ByteArrayOutputStream result = responseParser.convertToByteArrayOutputStream(responseObject);

        assertEquals(expectedResult.toString(), result.toString());
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
    public void convertToByteArrayOutputStreamCanConvertAResponseWithHeaders() throws IOException {
        buildResponseForHeadersTest();
        String rawResponseString =
                "HTTP/1.1 200 OK" + Whitespace.CRLF +
                "Content-Length: 0" + Whitespace.CRLF +
                "Date: Some Date" + Whitespace.CRLF +
                Whitespace.CRLF;

        ByteArrayOutputStream expectedResult = new ByteArrayOutputStream();
        expectedResult.write(rawResponseString.getBytes());

        ByteArrayOutputStream result = responseParser.convertToByteArrayOutputStream(responseObject);

        assertEquals(expectedResult.toString(), result.toString());
    }

    private void buildResponseForHeadersTest() {
        String header1Key = "Content-Length";
        String header1Value = "0";
        String header2Key = "Date";
        String header2Value = "Some Date";
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
            .addHeader(header1Key, header1Value)
            .addHeader(header2Key, header2Value);

        responseObject = builder.build();
    }

//    @Test public void stringifyAddsAMessageBodyIfTheResponseObjectHasABody() {
//        String header1Key = "Content-Length";
//        String header1Value = "5";
//        String body = "Hello";
//        buildResponseForBodyTest(header1Key, header1Value, body);
//
//        String result = responseParser.stringify(responseObject);
//
//        String expectedResult =
//                "HTTP/1.1 200 OK" + Whitespace.CRLF +
//                "Content-Length: 5" + Whitespace.CRLF +
//                        Whitespace.CRLF +
//                "Hello";
//        assertEquals(expectedResult, result);
//    }
//
//    private void buildResponseForBodyTest(String header1Key, String header1Value, String body) {
//        ResponseBuilder builder = new ResponseBuilder();
//        builder.addOKStatusLine()
//                .addHeader(header1Key, header1Value)
//                .addBody(body);
//
//        responseObject = builder.build();
//    }
}
