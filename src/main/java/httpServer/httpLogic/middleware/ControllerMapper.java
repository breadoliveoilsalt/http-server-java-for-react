package httpServer.httpLogic.middleware;

import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;
import httpServer.serverLogger.ServerLogger;

import java.lang.reflect.Constructor;

public class ControllerMapper extends Middleware {

    private final Router router;
    private Request request;
    private Response response;

    public ControllerMapper(Router router) {
        this.router = router;
    }

    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        try {
            if (response.statusCode != null) {
                response.controller = getControllerForPathRequested();
            }
            passToNextMiddleware(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Controller getControllerForPathRequested() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class controllerClass = (Class<Controller>) router.getControllerFor(request.getPath());
        Constructor<Controller> controllerConstructor = controllerClass.getConstructor(Request.class, Response.class);
        return controllerConstructor.newInstance(request, response);
    }

//    private Response determineResponse() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
//
//        if (request.wasUnparsable()) {
//            return new ExceptionsController().render400Response();
//        }
//
//        if (requestHasUnrecognizedMethod()) {
//            return new ExceptionsController().render501Response();
//        }
//
//        if (requestedResourceDoesNotExist()) {
//            return new ExceptionsController().render404Response();
//        }
//
//        mapRequestToController();
//
//        if (controllerDoesNotSupportTheMethod()) {
//            return new ExceptionsController().render405Response(controller);
//        }
//
//        return callControllerMethod();
//    }
//
//    private void populateHandlerFields(Request request) {
//        this.request = request;
//        if (!request.wasUnparsable()) {
//            this.pathRequested = request.getPath();
//            this.controllerMethodRequested = request.getHTTPMethod().toLowerCase();
//        }
//    }
//
//
//    private Response callControllerMethod() throws NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
//        Method methodToInvoke = controllerClass.getMethod(controllerMethodRequested);
//        return (Response) methodToInvoke.invoke(controller);
//    }
//
//    private boolean requestHasRecognizedMethod() {
//        return router.getAllRecognizedHTTPMethods().contains(controllerMethodRequested);
//    }
//
//    private boolean requestHasUnrecognizedMethod() {
//        return !router.getAllRecognizedHTTPMethods().contains(controllerMethodRequested);
//    }
//
//    private boolean controllerDoesNotSupportTheMethod() {
//        return requestHasRecognizedMethod() && !controller.getRecognizedMethods().contains(controllerMethodRequested);
//    }
//
//    private boolean requestedResourceDoesNotExist() {
//        return !router.getPaths().contains(pathRequested);
//    }
//
}
