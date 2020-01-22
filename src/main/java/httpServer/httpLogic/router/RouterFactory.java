package httpServer.httpLogic.router;
import httpServer.httpLogic.controllers.EchoBodyController;
import httpServer.httpLogic.controllers.GetWithBodyController;
import httpServer.httpLogic.controllers.SimpleGetController;

public class RouterFactory {

    public Router buildHTTPServerRouter() {
        return new RouterBuilder()
                .addPathAndController("/simple_get", SimpleGetController.class)
                .addPathAndController("/get_with_body", GetWithBodyController.class)
                .addPathAndController("/echo_body", EchoBodyController.class)
                .build();
    }

}
