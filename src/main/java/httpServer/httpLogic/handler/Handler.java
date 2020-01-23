package httpServer.httpLogic.handler;

import httpServer.httpLogic.controllers.ExceptionsController;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.router.Router;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Handler {

    private final Router router;

    public Handler(Router router) {
        this.router = router;
    }

//    public Response handle(Request request) throws Exception {
//        Callable<Response> action;
//
//        if (request.isInvalid()) {
//            action = ExceptionsController::build400Response;
//        } else if (validHEADRequest(request)) {
//            action = () -> MetaDataRequestController.buildHEADResponse(router, request);
//        } else if (validOPTIONSRequest(request)) {
//            action = () -> MetaDataRequestController.buildOPTIONSResponse(router, request);
//        } else if (hasUnrecognizedMethod(request)) {
//            action = ExceptionsController::build501Response;
//        } else {
//            action = router.getActionFor(request.getPath(), request.getMethod());
//        }
//
//        return action.call();
//    }

    // WORKS:
//    public Response handle(Request request) throws Exception {
//        String methodRequested = request.getMethod().toLowerCase();
//        Class controllerClass = router.getControllerFor(request.getPath());
//        Method methodToInvoke = controllerClass.getMethod(methodRequested, Request.class);
//        return (Response) methodToInvoke.invoke(null, request);
//    }

    public Response handle(Request request) throws Exception {
        if (request.wasUnparsable()) {
            return new ExceptionsController().render400Response();
        } else if (hasUnrecognizedMethod(request)) {
            return new ExceptionsController().render501Response();
        } else {
            return mapRequestToControllerAndReturnResponse(request);
        }
    }

    private Response mapRequestToControllerAndReturnResponse(Request request) throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        String methodRequested = request.getMethod().toLowerCase();
        Class controllerClass = router.getControllerFor(request.getPath());
        Constructor controllerConstructor = controllerClass.getConstructor(Router.class, Request.class);
        Object controller = controllerConstructor.newInstance(router, request);
        Method methodToInvoke = controllerClass.getMethod(methodRequested);
        return (Response) methodToInvoke.invoke(controller);
    }

    private boolean hasUnrecognizedMethod(Request request) {
        return !router.getAllRecognizedMethods().contains(request.getMethod());
    }

}
