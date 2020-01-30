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
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
            .addHeader("Content-Length", "0")
            .addHeader("Date", "Some Date");

        responseObject = builder.build();
    }

    @Test public void convertToByteArrayOutputStreamAddsAResponsesStringBody() throws IOException {
        buildResponseForStringBodyTest();

        String rawResponseString =
                "HTTP/1.1 200 OK" + Whitespace.CRLF +
                "Content-Length: 5" + Whitespace.CRLF +
                        Whitespace.CRLF +
                "Hello";

        ByteArrayOutputStream expectedResult = new ByteArrayOutputStream();
        expectedResult.write(rawResponseString.getBytes());

        ByteArrayOutputStream result = responseParser.convertToByteArrayOutputStream(responseObject);

        assertEquals(expectedResult.toString(), result.toString());
    }

    private void buildResponseForStringBodyTest() {
        ResponseBuilder builder = new ResponseBuilder();
        builder.addOKStatusLine()
                .addHeader("Content-Length", "5")
                .addBody("Hello");

        responseObject = builder.build();
    }
}
