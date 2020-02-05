package httpServerTests.httpLogicTests.responsesTests;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.constants.HTTPStatusMessages;
import httpServer.httpLogic.constants.HTTPVersions;
import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class ResponseParserTests {

    Response response;
    ResponseParser responseParser;

    @Before
    public void testInit() {
        responseParser = new ResponseParser();
    }

    @Test
    public void convertToByteArrayConvertsAResponseObjectToAByteArray() throws IOException {
        buildResponseForBasicStringTest();
        String expectedResult = "HTTP/1.1 200 OK" + Whitespace.CRLF + Whitespace.CRLF;

        byte[] result = responseParser.convertToByteArray(response);

        assertEquals(expectedResult, new String(result));
    }

   private void buildResponseForBasicStringTest() {
        response = new Response(
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

        byte[] result = responseParser.convertToByteArray(response);

        assertEquals(expectedResult, new String(result));
    }

    private void buildResponseForHeadersTest() {
        response = new Response();
        response.statusCode = HTTPStatusCodes.OK;
        response.statusMessage = HTTPStatusMessages.OK;
        response.httpVersion = HTTPVersions.versionImplemented;
        response.addHeader("Content-Length", "0");
        response.addHeader("Date", "Some Date");
    }

    @Test public void convertToByteArrayAddsAResponse_sStringBody() throws IOException {
        buildResponseForStringBodyTest();

        String expectedResult =
                "HTTP/1.1 200 OK" + Whitespace.CRLF +
                "Content-Length: 5" + Whitespace.CRLF +
                        Whitespace.CRLF +
                "Hello";

        byte[] result = responseParser.convertToByteArray(response);

        assertEquals(expectedResult, new String(result));
    }

    private void buildResponseForStringBodyTest() {
        response = new Response();
        response.statusCode = HTTPStatusCodes.OK;
        response.statusMessage = HTTPStatusMessages.OK;
        response.httpVersion = HTTPVersions.versionImplemented;
        response.addHeader("Content-Length", "5");
        response.stringBody = "Hello";
    }

    @Test public void convertToByteArrayOutputStreamAddsAResponse_sFile() throws IOException {
        File fileForTest = getFileForTest();
        buildResponseForFileTest(fileForTest);

        String rawExpectedMetaData =
                "HTTP/1.1 200 OK" + Whitespace.CRLF +
                "Content-Length: " + fileForTest.length() + Whitespace.CRLF +
                Whitespace.CRLF;

        ByteArrayOutputStream tempExpectedResultBuffer = new ByteArrayOutputStream();
        tempExpectedResultBuffer.write(rawExpectedMetaData.getBytes());
        tempExpectedResultBuffer.write(Files.readAllBytes(fileForTest.toPath()));

        byte[] expectedResult = tempExpectedResultBuffer.toByteArray();
        tempExpectedResultBuffer.close();

        byte[] result = responseParser.convertToByteArray(response);

        assertEquals(new String(expectedResult), new String(result));
    }

    private File getFileForTest() {
        URL urlToFileToWrite = this.getClass().getResource("fileToWriteForTests.txt");
        return new File(urlToFileToWrite.getPath());
    }

    private void buildResponseForFileTest(File fileForTest) {
        response = new Response();
        response.statusCode = HTTPStatusCodes.OK;
        response.statusMessage = HTTPStatusMessages.OK;
        response.httpVersion = HTTPVersions.versionImplemented;
        response.addHeader("Content-Length", Long.toString(fileForTest.length()));
        response.file = fileForTest;
    }

}
