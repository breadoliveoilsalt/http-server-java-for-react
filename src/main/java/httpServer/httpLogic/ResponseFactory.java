package httpServer.httpLogic;

public class ResponseFactory {

    public static Response buildSimpleResponse() {
       ResponseBuilder builder = new ResponseBuilder();
       builder.addOKStatusLine();
       return builder.build();
    }

}
