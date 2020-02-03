package httpServer.httpLogic.responses;

import httpServer.httpLogic.constants.Whitespace;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ResponseParser {

    private Response response;
    private String stringifiedMetaData;

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public byte[] convertToByteArray(Response response) throws IOException {
        this.response = response;
        writeMetaData();
        writeBody();
        return byteArrayOutputStream.toByteArray();
    }

    private void writeBody() throws IOException {
        if (response.stringBody != null) {
            byteArrayOutputStream.write(response.stringBody.getBytes());
        } else if (response.file != null) {
            FileInputStream fileInputStream = new FileInputStream(response.file);
            byte[] fileBuffer = new byte[Math.toIntExact(response.file.length())];
            fileInputStream.read(fileBuffer);
            byteArrayOutputStream.write(fileBuffer);
            fileInputStream.close();
        }
    }

    private void writeMetaData() throws IOException {
        stringifyMetaData();
        byteArrayOutputStream.write(stringifiedMetaData.getBytes());
    }

    private void stringifyMetaData() {
        stringifyStatusLine();
        stringifyHeaders();
        stringifyEndOfMetaData();
    }

    private void stringifyStatusLine() {
        stringifiedMetaData = response.httpVersion + Whitespace.SPACE + response.statusCode + Whitespace.SPACE + response.statusMessage + Whitespace.CRLF;
    }

    private void stringifyHeaders() {
        if (response.getHeaders() != null) {
            response.getHeaders().forEach(
                (key, value) -> stringifiedMetaData += key + ": " + value + Whitespace.CRLF
            );
        }
    }

    private void stringifyEndOfMetaData() {
        stringifiedMetaData += Whitespace.CRLF;
    }

}
