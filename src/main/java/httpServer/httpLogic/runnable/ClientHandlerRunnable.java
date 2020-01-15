package httpServer.httpLogic.runnable;

import httpServer.factory.AppFactory;
import httpServer.httpLogic.controllerLogic.Controller;
import httpServer.httpLogic.controllerLogic.ControllerFactory;
import httpServer.httpLogic.io.RequestReader;
import httpServer.httpLogic.io.ResponseWriter;
import httpServer.httpLogic.requests.RequestParser;
import httpServer.httpLogic.responses.ResponseParser;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
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
            handleClientRequest();
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

    private void handleClientRequest() throws Exception {
        Controller controller = new ControllerFactory().buildHTTPServerController();
        String rawClientRequest = new RequestReader().readInputStream(sokket);
        Request clientRequest = new RequestParser().parse(rawClientRequest);
        Response serverResponse = controller.handle(clientRequest);
        String writableResponse = new ResponseParser().stringify(serverResponse);
        new ResponseWriter().writeToOutputStream(sokket, writableResponse);
    }

}
