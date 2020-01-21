package httpServer.httpLogic.router;
import httpServer.httpLogic.controllers.GetWithBodyController;
import httpServer.httpLogic.controllers.SimpleGetController;
import httpServer.httpLogic.responses.ResponseFactory;

public class RouterFactory {

    public Router buildHTTPServerRouter() {
        RouterBuilder builder = new RouterBuilder();

        builder
            .createPath("/simple_get")
                .addMethodAndAction("GET", SimpleGetController::get);

        builder.createPath("/get_with_body")
                .addMethodAndAction("GET", GetWithBodyController::get);

        return builder.build();
    }

}
