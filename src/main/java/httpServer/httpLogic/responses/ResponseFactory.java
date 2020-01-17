package httpServer.httpLogic.responses;

import java.util.concurrent.Callable;

public class ResponseFactory {

    public static Response buildSimpleResponse() {
        return new ResponseBuilder()
                .addOKStatusLine()
                .build();
    }

    public static Response buildHEADResponseFor(Response response) throws Exception {
        return new ResponseBuilder()
                .addOKStatusLine()
                .setHeaders(response.getHeaders())
                .build();
    }
}
