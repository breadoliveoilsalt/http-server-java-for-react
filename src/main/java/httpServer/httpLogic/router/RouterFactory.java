package httpServer.httpLogic.router;
import httpServer.httpLogic.constants.Methods;
import httpServer.httpLogic.controllers.EchoBodyController;
import httpServer.httpLogic.controllers.GetWithBodyController;
import httpServer.httpLogic.controllers.SimpleGetController;
import httpServer.httpLogic.requests.Request;

import java.util.HashMap;
import java.util.Map;

public class RouterFactory {

    public Router buildHTTPServerRouter() {
        Map<String, Class> routeMap = new HashMap<>();
        routeMap.put("/echo_body", EchoBodyController.class);
        routeMap.put("/simple_get", SimpleGetController.class);
        return new Router(routeMap);
//        RouterBuilder builder = new RouterBuilder();
//
//        builder.createPath("/simple_get")
//                .addMethodAndAction(Methods.GET, SimpleGetController::get);
//
//        builder.createPath("/get_with_body")
//                .addMethodAndAction(Methods.GET, GetWithBodyController::get);
//
//        builder.createPath("/method_options")
//                .addMethodAndAction(Methods.GET, SimpleGetController::get);
//
//        builder.createPath("/method_options2")
//                .addMethodAndAction(Methods.GET, SimpleGetController::get)
//                .addMethodAndAction(Methods.PUT, SimpleGetController::get)
//                .addMethodAndAction(Methods.POST, SimpleGetController::get);

        // WHERE I GOT STUCK
//        builder.createPath("/echo_body")
//                .addMethodAndAction(Methods.PUT, (Request request) -> EchoBodyController.post(request));

//        return builder.build();
    }

}
