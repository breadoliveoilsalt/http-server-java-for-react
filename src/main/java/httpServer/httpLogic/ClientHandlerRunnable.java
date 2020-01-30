package httpServer.httpLogic;

import httpServer.httpLogic.controllerHandler.ControllerHandler;
import httpServer.httpLogic.middleware.IndexDotHTMLFileFinder;
import httpServer.router.Router;
import httpServer.router.RouterFactory;
import httpServer.httpLogic.io.RequestReader;
import httpServer.httpLogic.io.ResponseWriter;
import httpServer.httpLogic.requests.RequestParser;
import httpServer.httpLogic.responses.ResponseParser;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.serverSocketLogic.HTTPServerLogicObject;
import httpServer.serverLogger.ServerLogger;
import httpServer.serverSocketLogic.wrappers.*;

import java.io.IOException;

public class ClientHandlerRunnable implements Runnable, HTTPServerLogicObject {

    private final Sokket sokket;
    private final ServerLogger logger;

    public ClientHandlerRunnable(Sokket sokket, ServerLogger logger) {
        this.sokket = sokket;
        this.logger = logger;
    }

    public void run() {
        try {
            handleClientRequest();
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
        Router router = new RouterFactory().buildHTTPServerRouter();
        String rawClientRequest = new RequestReader().readInputStream(sokket);
        Request clientRequest = new RequestParser().parse(rawClientRequest);
        Response serverResponse = new Response();
        new IndexDotHTMLFileFinder().handle(clientRequest, serverResponse);
        if (serverResponse.statusCode == null) {
            serverResponse = new ControllerHandler(router, logger).handle(clientRequest);
        }
        byte[] rawResponse = new ResponseParser().convertToByteArray(serverResponse);
        new ResponseWriter().writeToOutputStream(sokket, rawResponse);
    }

}
