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

import java.io.File;
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
        File textFile = tempFolder.newFile(textFileName);
        response.file = textFile;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextPlain));
    }

    @Test
    public void handleAddsTextHTMLContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAHTMLFile() throws IOException {
        String fileName = "htmlFile.html";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
    }

    @Test
    public void handleAddsTextHTMLContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAHTMFile() throws IOException {
        String fileName = "htmFile.htm";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextHTML));
    }

    @Test
    public void handleAddsTextCSSContentTypeToAnOKResponseIfTheResponseIsAssociatedWithACSSFile() throws IOException {
        String fileName = "cssFile.css";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextCSS));
    }

    @Test
    public void handleAddsTextJavaScriptContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJSFile() throws IOException {
        String fileName = "javaScriptFile.js";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.TextJavaScript));
    }

    @Test
    public void handleAddsApplicationJSONContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJSONFile() throws IOException {
        String fileName = "jsonFile.json";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ApplicationJSON));
    }

    @Test
    public void handleAddsApplicationPDFContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAPDFFile() throws IOException {
        String fileName = "pdfFile.pdf";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ApplicationPDF));
    }

    @Test
    public void handleAddsImageJPEGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJPGFile() throws IOException {
        String fileName = "jpgFile.jpg";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImageJPEG));
    }

    @Test
    public void handleAddsImageJPEGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAJPEGFile() throws IOException {
        String fileName = "jpegFile.jpeg";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImageJPEG));
    }

    @Test
    public void handleAddsImagePNGContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAPNGFile() throws IOException {
        String fileName = "pngFile.png";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImagePNG));
    }

    @Test
    public void handleAddsImageGIFContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAGIFFile() throws IOException {
        String fileName = "gifFile.gif";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.ImageGIF));
    }

    @Test
    public void handleAddsAudioWAVContentTypeToAnOKResponseIfTheResponseIsAssociatedWithAWAVFile() throws IOException {
        String fileName = "wavFile.wav";
        File file = tempFolder.newFile(fileName);
        response.file = file;

        contentTypeInserter.handle(request, response);

        assertTrue(response.hasHeader(HTTPHeaders.ContentType, HTTPContentTypes.AudioWAV));
    }
//- Audio (.mp3, .wav)
//- Video (.mov, .mp4)
//X HTML (.htm, .html)
//X CSS (.css)
//X Javascript (.js)
//X JSON (.json)
//X Plan text (.txt)
//X PDF Document (.pdf)
//X Images (.jpg, .png, .gif)
}
