package httpServer.httpLogic.router;
import httpServer.httpLogic.responses.ResponseFactory;

public class RouterFactory {

    public Router buildHTTPServerRouter() {
        RouterBuilder builder = new RouterBuilder();

        builder
            .createPath("/simple_get")
            .addMethodAndAction("GET", () -> ResponseFactory.buildSimpleResponse());

        builder.createPath("/get_with_body")
                .addMethodAndAction("GET", () -> ResponseFactory.buildSimpleResponseWithBody());

        return builder.build();
    }

}
