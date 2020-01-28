package httpServer.serverSocketLogic.serverLogger;

import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ServerLogger {

    private final OutputStream outputStream;
    private final ArrayList<String> logList;

    public ServerLogger(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.logList = new ArrayList<>();
    }

    public ArrayList<String> getLogList() {
        return logList;
    }

    public ServerLogger print(String message) {
        try {
            addToLogList(message + newLine());
            writeToOutputStream(message + newLine());
            return this;
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }
    }


    private void addToLogList(String message) {
        logList.add(message);
    }

    private String newLine() {
       return Whitespace.CRLF;
    }

    private void writeToOutputStream(String message) throws IOException {
        outputStream.write(message.getBytes());
        outputStream.flush();
    }

    public void logServerInit(int port) {
        try {
            String message = "The server is now listening on port " + port + "." + newLine();
            addToLogList(message);
            writeToOutputStream(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logServerShuttingDown() {
        try {
            String message = "The server is shutting down." + newLine();
            addToLogList(message);
            writeToOutputStream(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void logRequestAndResponse(Request request, Response response) {
        String host = populateHost(request);
        try {
            String message =
                    Whitespace.DIVIDER +
                    newLine() +
                    host +
                    " made a " +
                    request.getMethod() +
                    " request to " +
                    request.getPath() +
                    "." +
                    newLine() +
                    "The server responded with a " +
                    response.getStatusCode() +
                    " status code." +
                    newLine() +
                    Whitespace.DIVIDER;
            addToLogList(message);
            writeToOutputStream(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String populateHost(Request request) {
        if (request.getHeaders() != null && request.getHeaders().containsKey("Host")) {
            return request.getHeaderValue("Host");
        } else {
            return "Unidentified client";
        }
    }

}
