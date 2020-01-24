package httpServer.httpLogic.handler;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.controllers.ExceptionsController;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.router.Router;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Handler {

    private final Router router;
    private Request request;

    public Handler(Router router) {
        this.router = router;
    }

    public Response handle(Request request) throws Exception {
        this.request = request;
        if (request.wasUnparsable()) {
            return new ExceptionsController().render400Response();
        } else if (hasUnrecognizedMethod()) {
            return new ExceptionsController().render501Response();
        } else {
            return mapRequestToControllerAndReturnResponse();
        }
    }

    private Response mapRequestToControllerAndReturnResponse() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        String methodRequested = request.getMethod().toLowerCase();
        Class<?> controllerClass = router.getControllerFor(request.getPath());
        Constructor controllerConstructor = controllerClass.getConstructor(Router.class, Request.class);
        Object controller = controllerConstructor.newInstance(router, request);
        Method methodToInvoke = controllerClass.getMethod(methodRequested);
        return (Response) methodToInvoke.invoke(controller);
    }

    private boolean hasUnrecognizedMethod() {
        return !router.getAllRecognizedMethods().contains(request.getMethod());
    }

}
