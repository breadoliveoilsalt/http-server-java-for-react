package httpServer.httpLogic.middleware;

import httpServer.httpLogic.constants.HTTPHeaders;
import httpServer.httpLogic.constants.HTTPStatusCodes;
import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.router.Router;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
                askControllerToRespondToHTTPMethodRequested();
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

    private void askControllerToRespondToHTTPMethodRequested() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (controller.respondsTo(request.getHTTPMethod())) {
            callControllerMethod();
            response.statusCode = HTTPStatusCodes.OK;
        } else {
            response.statusCode = HTTPStatusCodes.MethodNotAllowed;
            response.addHeader(HTTPHeaders.Allow, controller.getStringOfRecognizedMethods());
        }
    }

    private void callControllerMethod() throws NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        Method methodToInvoke = controller.getClass().getMethod(request.getHTTPMethod().toLowerCase());
        methodToInvoke.invoke(controller);
    }

}
