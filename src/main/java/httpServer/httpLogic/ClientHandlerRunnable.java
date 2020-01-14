package httpServer.httpLogic;

import httpServer.factory.AppFactory;
import httpServer.httpLogic.routes.RouteMap;
import httpServer.httpLogic.routes.RouteMapFactory;
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
        RouteMap routes = new RouteMapFactory().buildHTTPServerRoutes();

        String rawClientRequest = ClientRequestReader.readInputStream(sokket);

        Request clientRequest = new RequestParser().parse(rawClientRequest);
        Response serverResponse = RequestHandler.handle(clientRequest, routes);
        String writableResponse = new ResponseConverter().stringify(serverResponse);
        Writer writer = new JavaPrintWriterWrapper(sokket.getOutputStream());
        // UPTO: CREATE something where you pass sokket and stringified response;

        writer.printLine(writableResponse);
    }

}
