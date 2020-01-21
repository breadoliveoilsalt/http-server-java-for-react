package httpServer.httpLogic.router;
import httpServer.httpLogic.constants.Methods;
import httpServer.httpLogic.controllers.GetWithBodyController;
import httpServer.httpLogic.controllers.SimpleGetController;

public class RouterFactory {

    public Router buildHTTPServerRouter() {
        RouterBuilder builder = new RouterBuilder();

        builder.createPath("/simple_get")
                .addMethodAndAction(Methods.GET, SimpleGetController::get);

        builder.createPath("/get_with_body")
                .addMethodAndAction(Methods.GET, GetWithBodyController::get);

        builder.createPath("/method_options")
                .addMethodAndAction(Methods.GET, SimpleGetController::get);

        builder.createPath("/method_options2")
                .addMethodAndAction(Methods.GET, SimpleGetController::get)
                .addMethodAndAction(Methods.PUT, SimpleGetController::get)
                .addMethodAndAction(Methods.POST, SimpleGetController::get);

        return builder.build();
    }

}
