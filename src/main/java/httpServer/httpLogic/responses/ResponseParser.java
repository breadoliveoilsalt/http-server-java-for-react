package httpServer.httpLogic.responses;

import httpServer.httpLogic.constants.Whitespace;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ResponseParser {

    private Response response;
    private String stringifiedResponse;
    private String stringifiedMetaData;

    public String stringify(Response response) {
        this.response = response;
        stringifyStatusLine();
        stringifyHeaders();
        stringifyEndOfMetaData();
        addBody();
        return stringifiedResponse;
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

    private void addBody() {
       if (response.getStringBody() != null) {
           stringifiedResponse += response.stringBody;
       }
    }

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public byte[] convertToByteArray(Response response) throws IOException {
        this.response = response;
        stringifyMetaData();
        byteArrayOutputStream.write(stringifiedMetaData.getBytes());
        if (response.getStringBody() != null) {
            byteArrayOutputStream.write(response.getStringBody().getBytes());
        } else if (response.file != null) {
            FileInputStream fileInputStream = new FileInputStream(response.file);
            byte[] fileBuffer = new byte[Math.toIntExact(response.file.length())];
            fileInputStream.read(fileBuffer);
            byteArrayOutputStream.write(fileBuffer);
            fileInputStream.close();
        }
       return byteArrayOutputStream.toByteArray();
    }

    private void stringifyMetaData() {
        stringifyStatusLine();
        stringifyHeaders();
        stringifyEndOfMetaData();
    }

}
