package httpServer.httpLogic.router;
import httpServer.httpLogic.controllers.*;

public class RouterFactory {

    public Router buildHTTPServerRouter() {
        return new RouterBuilder()
            .addPathAndController("/simple_get", SimpleGetController.class)
            .addPathAndController("/get_with_body", GetWithBodyController.class)
            .addPathAndController("/echo_body", EchoBodyController.class)
            .addPathAndController("/method_options", MethodOptionsController.class)
            .addPathAndController("/method_options2", MethodOptions2Controller.class)
            .build();
    }

}