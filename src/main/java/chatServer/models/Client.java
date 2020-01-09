package chatServer.models;

import chatServer.factory.AppFactory;
import chatServer.wrappers.Reader;
import chatServer.wrappers.Sokket;
import chatServer.wrappers.Writer;

import java.io.IOException;

public class Client {

    private Sokket sokket;
    protected String clientName;
    protected String getClientName() {
        return clientName;
    }

    protected Writer writerToClient;
    protected Reader readerFromClient;

    protected Client () {}

    public Client(Sokket sokket, AppFactory factory) throws IOException {
        this.sokket = sokket;
        this.writerToClient = factory.createWriter(sokket.getOutputStream());
        this.readerFromClient = factory.createReader(sokket.getInputStream());
        askForClientName();
    }

    public void sendMessage(String message) {
        writerToClient.printLine(message);
    }

    public String getMessage() throws IOException {
        return readerFromClient.readLine();
    }

    private void askForClientName() throws IOException {
        writerToClient.printLine("\n>> What is your name?\n");
        clientName = readerFromClient.readLine();
        writerToClient.printLine(
            "\n>> Welcome to the Chat Room, " + clientName + "!\n" +
            "\n>> Type 'exit!' and hit return to disconnect.  \n");
    }

    public void leave() throws IOException {
        sokket.close();
    }

}
