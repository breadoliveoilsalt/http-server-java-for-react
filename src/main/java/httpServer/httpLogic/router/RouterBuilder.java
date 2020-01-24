package httpServer.httpLogic.router;

import java.util.HashMap;
import java.util.Map;

public class RouterBuilder {

    private final Map<String, Class> routeMap;

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
