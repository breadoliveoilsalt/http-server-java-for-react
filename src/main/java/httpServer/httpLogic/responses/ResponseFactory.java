package httpServer.httpLogic.responses;

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

    public static Response build501Response() {
        return new ResponseBuilder()
                .addStatusCode("501")
                .addStatusMessage("Not Implemented")
                .addBody("501 Error: Method Not Implemented")
                .build();
    }

    public static Response build400Response() {
        return new ResponseBuilder()
                .addStatusCode("400")
                .addStatusMessage("Bad Request")
                .addBody("400 Error: Bad Request Submitted")
                .build();
    }

}
