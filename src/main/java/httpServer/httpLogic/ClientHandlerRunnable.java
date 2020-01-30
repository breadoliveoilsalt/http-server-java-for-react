package httpServer.httpLogic;

import httpServer.httpLogic.handler.Handler;
import httpServer.httpLogic.middleware.IndexDotHTMLFileFinder;
import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterFactory;
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
        Response serverResponse = new Handler(router, logger).handle(clientRequest);
        new IndexDotHTMLFileFinder().handle(clientRequest, serverResponse);
        String writableResponse = new ResponseParser().stringify(serverResponse);
        new ResponseWriter().writeToOutputStream(sokket, writableResponse);
    }

}
