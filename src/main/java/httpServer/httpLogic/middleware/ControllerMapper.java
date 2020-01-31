package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ControllerMapper extends Middleware {

    private final Router router;
    private Request request;
    private Response response;
    private Controller controller;

    public ControllerMapper(Router router) {
        this.router = router;
    }

    public void handle(Request request, Response response) {
        try {
            if (response.hasUndeterminedStatus()) {
                this.request = request;
                this.response = response;
                getControllerForPathRequested();
                if (controller.respondsTo(request.getHTTPMethod())) {
                    response.statusCode = HTTPStatusCodes.OK;
                    callControllerMethod();
                } else {
                    response.statusCode = HTTPStatusCodes.MethodNotAllowed;
                    response.addHeader("Allow", controller.getStringOfRecognizedMethods());
                }
            }
            passToNextMiddleware(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getControllerForPathRequested() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Class controllerClass = (Class<Controller>) router.getControllerFor(request.getPath());
        Constructor<Controller> controllerConstructor = controllerClass.getConstructor(Request.class, Response.class);
        controller = controllerConstructor.newInstance(request, response);
    }

    private void callControllerMethod() throws NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Method methodToInvoke = controller.getClass().getMethod(request.getHTTPMethod());
        methodToInvoke.invoke(controller);
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
