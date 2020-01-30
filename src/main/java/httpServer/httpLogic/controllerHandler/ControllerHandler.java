package httpServer.httpLogic.controllerHandler;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.controllers.ExceptionsController;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;
import httpServer.serverLogger.ServerLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ControllerHandler {

    private final Router router;
    private Request request;
    private final ServerLogger logger;
    private String pathRequested;
    private String controllerMethodRequested;
    private Class<Controller> controllerClass;
    private Controller controller;

    public ControllerHandler(Router router, ServerLogger logger) {
        this.router = router;
        this.logger = logger;
    }

    public Response handle(Request request) throws Exception {
        populateHandlerFields(request);
        Response response = determineResponse();
        logger.logRequestAndResponse(request, response);
        return response;
    }

    private Response determineResponse() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {

        if (request.wasUnparsable()) {
            return new ExceptionsController().render400Response();
        }

        if (requestHasUnrecognizedMethod()) {
            return new ExceptionsController().render501Response();
        }

        if (requestedResourceDoesNotExist()) {
            return new ExceptionsController().render404Response();
        }

        mapRequestToController();

        if (controllerDoesNotSupportTheMethod()) {
            return new ExceptionsController().render405Response(controller);
        }

        return callControllerMethod();
    }

    private void populateHandlerFields(Request request) {
        this.request = request;
        if (!request.wasUnparsable()) {
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

    private boolean requestedResourceDoesNotExist() {
        return !router.getPaths().contains(pathRequested);
    }

}
