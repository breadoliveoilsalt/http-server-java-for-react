package httpServer.httpLogic.controllers;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.router.Router;

public abstract class Controller {

    protected Router router;
    protected Request request;

    public Controller(Router router, Request request) {
        this.router = router;
        this.request = request;
    }
}
