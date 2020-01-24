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
    private String pathRequested;
    private String controllerMethodRequested;
    private Class<Controller> controllerClass;
    private Controller controller;

    public Handler(Router router) {
        this.router = router;
    }

    public Response handle(Request request) throws Exception {

        if (request.wasUnparsable()) {
            return new ExceptionsController().render400Response();
        }

        populateHandlerFields(request);

        if (requestHasUnrecognizedMethod()) {
            return new ExceptionsController().render501Response();
        }

        mapRequestToController();

        if (controllerDoesNotSupportTheMethod()) {
            return new ExceptionsController().render405Response();
        }

        return callControllerMethod();
    }

    private void populateHandlerFields(Request request) {
        if (!request.wasUnparsable()) {
            this.request = request;
            this.pathRequested = request.getPath();
            this.controllerMethodRequested = request.getMethod().toLowerCase();
        }
    }

    private void mapRequestToController() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        controllerClass = (Class<Controller>) router.getControllerFor(pathRequested);
        Constructor controllerConstructor = controllerClass.getConstructor(Router.class, Request.class);
        controller = (Controller) controllerConstructor.newInstance(router, request);
    }

    private Response callControllerMethod() throws NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Method methodToInvoke = controllerClass.getMethod(controllerMethodRequested);
        return (Response) methodToInvoke.invoke(controller);
    }

    private boolean requestHasRecognizedMethod() {
        return router.getAllRecognizedMethods().contains(controllerMethodRequested);
    }

    private boolean requestHasUnrecognizedMethod() {
        return !router.getAllRecognizedMethods().contains(controllerMethodRequested);
    }

    private boolean controllerDoesNotSupportTheMethod() {
        return requestHasRecognizedMethod() && !controller.getRecognizedMethods().contains(controllerMethodRequested);
    }

}
