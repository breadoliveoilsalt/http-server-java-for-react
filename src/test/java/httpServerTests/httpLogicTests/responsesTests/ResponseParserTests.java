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
    public void convertToByteArrayConvertsAResponseObjectToAByteArray() throws IOException {
        buildResponseForBasicStringTest();
        String expectedResult = "HTTP/1.1 200 OK" + Whitespace.CRLF + Whitespace.CRLF;

        byte[] result = responseParser.convertToByteArray(responseObject);

        assertEquals(expectedResult, new String(result));
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
    public void convertToByteArrayCanConvertAResponseWithHeaders() throws IOException {
        buildResponseForHeadersTest();
        String expectedResult =
                "HTTP/1.1 200 OK" + Whitespace.CRLF +
                "Content-Length: 0" + Whitespace.CRLF +
                "Date: Some Date" + Whitespace.CRLF +
                Whitespace.CRLF;

        byte[] result = responseParser.convertToByteArray(responseObject);

        assertEquals(expectedResult, new String(result));
    }

    private void buildResponseForHeadersTest() {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
            .addHeader("Content-Length", "0")
            .addHeader("Date", "Some Date");

        responseObject = builder.build();
    }

    @Test public void convertToByteArrayAddsAResponsesStringBody() throws IOException {
        buildResponseForStringBodyTest();

        String expectedResult =
                "HTTP/1.1 200 OK" + Whitespace.CRLF +
                "Content-Length: 5" + Whitespace.CRLF +
                        Whitespace.CRLF +
                "Hello";

        byte[] result = responseParser.convertToByteArray(responseObject);

        assertEquals(expectedResult, new String(result));
    }

    private void buildResponseForStringBodyTest() {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
                .addHeader("Content-Length", "5")
                .addBody("Hello");

        responseObject = builder.build();
    }

//    @Test public void convertToByteArrayOutputStreamAddsAResponsesFile() throws IOException {
//        buildResponseForStringBodyTest();
//
//        String rawResponseString =
//                "HTTP/1.1 200 OK" + Whitespace.CRLF +
//                        "Content-Length: 5" + Whitespace.CRLF +
//                        Whitespace.CRLF +
//                        "Hello";
//
//        ByteArrayOutputStream expectedResult = new ByteArrayOutputStream();
//        expectedResult.write(rawResponseString.getBytes());
//
//        ByteArrayOutputStream result = responseParser.convertToByteArray(responseObject);
//
//        assertEquals(expectedResult.toString(), result.toString());
//    }
}
