package httpServer.httpLogic.responses;

import httpServer.httpLogic.constants.Whitespace;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ResponseParser {

    private Response response;
    private String stringifiedMetadata;

    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public byte[] convertToByteArray(Response response) throws IOException {
        this.response = response;
        writeMetadata();
        writeBody();
        return byteArrayOutputStream.toByteArray();
    }

    private void writeMetadata() throws IOException {
        stringifyMetadata();
        byteArrayOutputStream.write(stringifiedMetadata.getBytes());
    }

    private void stringifyMetadata() {
        stringifyStatusLine();
        stringifyHeaders();
        stringifyEndOfMetadata();
    }

    private void stringifyStatusLine() {
        stringifiedMetadata = response.httpVersion + Whitespace.SPACE + response.statusCode + Whitespace.SPACE + response.statusMessage + Whitespace.CRLF;
    }

    private void stringifyHeaders() {
        if (response.getHeaders() != null) {
            response.getHeaders().forEach(
                    (key, value) -> stringifiedMetadata += key + ": " + value + Whitespace.CRLF
            );
        }
    }

    private void stringifyEndOfMetadata() {
        stringifiedMetadata += Whitespace.CRLF;
    }

    private void writeBody() throws IOException {
        if (response.stringBody != null) {
            writeStringBodyToByteArray();
        } else if (response.file != null) {
            writeFileToByteArray();
        }
    }

    private void writeStringBodyToByteArray() throws IOException {
        byteArrayOutputStream.write(response.stringBody.getBytes());
    }

    private void writeFileToByteArray() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(response.file);
        byte[] fileBuffer = new byte[Math.toIntExact(response.file.length())];
        fileInputStream.read(fileBuffer);
        byteArrayOutputStream.write(fileBuffer);
        fileInputStream.close();
    }

}
