package httpServer.httpLogic.router;

import httpServer.httpLogic.controllers.Controller;

import java.util.HashMap;
import java.util.Map;

public class RouterBuilder {

    private final Map<String, Class<Controller>> routeMap;

    public RouterBuilder() {
        this.routeMap = new HashMap<>();
    }

    public RouterBuilder addPathAndController(String path, Class controllerClass) {
        routeMap.put(path, controllerClass);
        return this;
    }

    public Router build() {
        return new Router(routeMap);
    }

}
