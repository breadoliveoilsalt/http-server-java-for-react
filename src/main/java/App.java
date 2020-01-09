import chatServer.factory.ChatServerAppFactory;
import chatServer.factory.AppFactory;
import chatServer.logic.ChatServerInit;

import java.io.IOException;

class App {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        AppFactory factory = new ChatServerAppFactory();

        new ChatServerInit(port, factory).run();
    }

}