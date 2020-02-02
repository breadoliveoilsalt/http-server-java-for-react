package httpServer.httpLogic.controllers;

import httpServer.httpLogic.middleware.FileFinder;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

import java.io.File;

public class RootPathController extends Controller {

    public RootPathController(Request request, Response response) {
        super(request, response);
    }

    public Response get() {
        request.setPath("/index.html");
//        this.basePath = System.getProperty("user.dir");
//        File file = new File(basePath + request.getPath());
//        response.file = file;
        System.out.println("Path from Controller => " + request.getPath());
        System.out.println(response.hasUndeterminedStatus());
        System.out.println(response.statusCode);
        new FileFinder().handle(request, response);
        return response;
    }

}
