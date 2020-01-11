package httpServer.logic;

import httpServer.factory.AppFactory;
import httpServer.wrappers.Sokket;

public class ClientInitRunnable implements Runnable, HTTPServerLogicObject {

    private final Sokket sokket;
    private final AppFactory factory;

    public ClientInitRunnable(Sokket sokket, AppFactory factory) {
        this.sokket = sokket;
        this.factory = factory;
    }
    public void run() {
        System.out.println("Running");
    }

}
