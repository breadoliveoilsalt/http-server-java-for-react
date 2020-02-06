package httpServerTests.httpLogicTests.middlewareTests;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.middleware.ContentTypeInserter;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.junit.Assert.*;

public class ContentTypeInserterTests {

    private Request request;
    private Response response;
    private ContentTypeInserter contentTypeInserter;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void testInit() {
        request = new RequestBuilder().build();
        response = new Response();
        response.statusCode = HTTPStatusCodes.OK;
        contentTypeInserter = new ContentTypeInserter();
    }

    @Test
    public void handleCallsTheNextMiddlewareInTheChainIfOneExists() {
        MockMiddleware nextMiddleware = new MockMiddleware();
        contentTypeInserter.setNext(nextMiddleware);

        contentTypeInserter.handle(request, response);

        assertTrue(nextMiddleware.handleWasCalled);
    }

    @Test
    public void handleAddsTextPlainContentTypeToAnOKResponseIfThereIsAStringBody() {
        response.stringBody = "Some text";

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextPlain));
    }

    @Test
    public void handleAddsTextPlainContentTypeToAnOKResponseIfTheResponseIsAssociatedWithATxtFile() throws IOException {
        String textFileName = "textFile.txt";
        response.file = tempFolder.newFile(textFileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextPlain));
    }

    @Test
    public void handleAddsTextHTMLContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAnHTMLFile() throws IOException {
        String fileName = "htmlFile.html";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
    }

    @Test
    public void handleAddsTextHTMLContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAnHTMFile() throws IOException {
        String fileName = "htmFile.htm";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
    }

    @Test
    public void handleAddsTextCSSContentTypeToAnOKResponseIfTheResponseIsAssociatedWithACSSFile() throws IOException {
        String fileName = "cssFile.css";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextCSS));
    }

    @Test
    public void handleAddsTextJavaScriptContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJSFile() throws IOException {
        String fileName = "javaScriptFile.js";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextJavaScript));
    }

    @Test
    public void handleAddsApplicationJSONContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJSONFile() throws IOException {
        String fileName = "jsonFile.json";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ApplicationJSON));
    }

    @Test
    public void handleAddsApplicationPDFContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAPDFFile() throws IOException {
        String fileName = "pdfFile.pdf";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ApplicationPDF));
    }

    @Test
    public void handleAddsImageJPEGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJPGFile() throws IOException {
        String fileName = "jpgFile.jpg";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImageJPEG));
    }

    @Test
    public void handleAddsImageJPEGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJPEGFile() throws IOException {
        String fileName = "jpegFile.jpeg";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImageJPEG));
    }

    @Test
    public void handleAddsImagePNGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAPNGFile() throws IOException {
        String fileName = "pngFile.png";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImagePNG));
    }

    @Test
    public void handleAddsImageGIFContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAGIFFile() throws IOException {
        String fileName = "gifFile.gif";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImageGIF));
    }

    @Test
    public void handleAddsAudioWAVContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAWAVFile() throws IOException {
        String fileName = "wavFile.wav";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.AudioWAV));
    }

    @Test
    public void handleAddsAudioMPEGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAMP3File() throws IOException {
        String fileName = "mp3File.mp3";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.AudioMPEG));
    }

    @Test
    public void handleAddsVideoMP4ContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAMP4File() throws IOException {
        String fileName = "mp4File.mp4";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.VideoMP4));
    }

    @Test
    public void handleAddsVideoQuickTimeContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAMOVFile() throws IOException {
        String fileName = "movFile.mov";
        response.file = tempFolder.newFile(fileName);

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.VideoQuickTime));
    }

}
