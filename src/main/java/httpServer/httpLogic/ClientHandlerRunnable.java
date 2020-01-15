package httpServer.httpLogic;

import httpServer.factory.AppFactory;
import httpServer.httpLogic.controllerLogic.Controller;
import httpServer.httpLogic.controllerLogic.ControllerFactory;
import httpServer.serverSocketLogic.HTTPServerLogicObject;
import httpServer.wrappers.*;

import java.io.IOException;

public class ClientHandlerRunnable implements Runnable, HTTPServerLogicObject {

    private final Sokket sokket;
    private final AppFactory factory;

    public ClientHandlerRunnable(Sokket sokket, AppFactory factory) {
        this.sokket = sokket;
        this.factory = factory;
    }

    public void run() {
        try {
            printMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sokket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMessage() throws Exception {
        System.out.println("Someone tried to connect!");
        Controller controller = new ControllerFactory().buildHTTPServerController();

        String rawClientRequest = new ClientRequestReader().readInputStream(sokket);

        Request clientRequest = new RequestParser().parse(rawClientRequest);
        Response serverResponse = controller.handle(clientRequest);
        String writableResponse = new ResponseConverter().stringify(serverResponse);
        Writer writer = new JavaPrintWriterWrapper(sokket.getOutputStream());
        // UPTO: CREATE something where you pass sokket and stringified response;

        writer.printLine(writableResponse);
    }

}
