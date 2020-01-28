package httpServer.serverSocketLogic.serverLogger;

import httpServer.httpLogic.constants.Whitespace;
import httpServer.httpLogic.requests.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ServerLogger {

    private OutputStream outputStream;
    private ArrayList<String> logList;

    public ServerLogger(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.logList = new ArrayList<>();
    }

    public ArrayList<String> getLogList() {
        return logList;
    }

    public ServerLogger print(String message) {
        try {
            logList.add(message + newLine());
            writeToOutputStream(message + newLine());
            return this;
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }
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
            String message = "The server is now listening on port " + String.valueOf(port) + newLine();
            logList.add(message);
            writeToOutputStream(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logServerShuttingDown() {
        try {
            String message = "The server is shutting down" + newLine();
            logList.add(message);
            writeToOutputStream(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logClientConnection(Request request) {
        try {
            String hostName = request.getHeaderValue("Host");
            String message =
                    hostName +
                    " made a " +
                    request.getMethod() +
                    " request to " +
                    request.getPath() +
                    newLine();
            logList.add(message);
            writeToOutputStream(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
