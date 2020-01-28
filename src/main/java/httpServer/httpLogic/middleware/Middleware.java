package httpServer.httpLogic.middleware;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public abstract class Middleware {

    protected Middleware next;

    public void handle(Request request, Response response) {
    }

    public Middleware setNext(Middleware middleware) {
        this.next = middleware;
        return this.next;
    }

    public void passToNext(Request request, Response response) {
        if (this.next != null) {
            this.next.handle(request, response);
        }
    }
    
}
