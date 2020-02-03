package httpServer.httpLogic;

import httpServer.httpLogic.middlewareConfig.ResponseBuildingMiddleware;
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
        Request request = new RequestParser().parse(rawClientRequest);
        Response response = new Response();
        new ResponseBuildingMiddleware().runWithBasicConfig(router, request, response);
        logger.logRequestAndResponse(request, response);
        byte[] rawResponse = new ResponseParser().convertToByteArray(response);
        new ResponseWriter().writeToOutputStream(sokket, rawResponse);
    }

}
