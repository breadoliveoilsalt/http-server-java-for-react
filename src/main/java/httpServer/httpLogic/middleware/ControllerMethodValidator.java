package httpServer.httpLogic.middleware;


import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;

public class ControllerMethodValidator extends Middleware {

    private Request request;
    private Response response;

    @Override
    public void handle(Request request, Response response) {
        this.request = request;
        this.response = response;
        if (response.hasUndeterminedStatus() && controllerDoesNotRecognizeMethodRequested()) {
            response.statusCode = HTTPStatusCodes.MethodNotAllowed;

        }
    }

    private boolean controllerDoesNotRecognizeMethodRequested() {
        return !response.controller.getRecognizedMethods().contains(request.getHTTPMethod());
    }
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
