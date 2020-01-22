package httpServer.httpLogic.handler;

import httpServer.httpLogic.constants.Methods;
import httpServer.httpLogic.controllers.Controller;
import httpServer.httpLogic.controllers.ExceptionsController;
import httpServer.httpLogic.controllers.MetaDataRequestController;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.router.Router;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Handler {

    private final Router router;

    public Handler(Router router) {
        this.router = router;
    }

    // COMMENTS

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

//    {"/some_path": SomePathController,
//    "/another_path": AnotherPathController}

    // WORKS:
//    public Response handle(Request request) throws Exception {
//        String methodRequested = request.getMethod().toLowerCase();
//        Class controllerClass = router.getControllerFor(request.getPath());
//        Method methodToInvoke = controllerClass.getMethod(methodRequested, Request.class);
//        return (Response) methodToInvoke.invoke(null, request);
//    }

    public Response handle(Request request) throws Exception {
        String methodRequested = request.getMethod().toLowerCase();
        Class controllerClass = router.getControllerFor(request.getPath());
        Constructor controllerConstructor = controllerClass.getConstructor(Router.class, Request.class);
        Object controller = controllerConstructor.newInstance(router, request);
        Method methodToInvoke = controllerClass.getMethod(methodRequested);
        return (Response) methodToInvoke.invoke(controller);
//        Method methodToInvoke = methodToInvoke.invoke(controller);
//        Method methodToInvoke = controller.getDeclaredMethod(methodRequested);
//        return (Response) methodToInvoke.invoke(null, request);
    }

//        if (controllerClass.getMethods().contains())
//        Constructor<Controller> controllerConstructor = controllerClass.getConstructor(Router.class, Request.class);
//        Controller controller = controllerConstructor.newInstance(router, request);

//    }

//    private boolean validHEADRequest(Request request)
//        return request.getMethod().equals(Methods.HEAD) && router.getMethodsFor(request.getPath()).contains(Methods.GET);
//    }
//
//    private boolean validOPTIONSRequest(Request request) {
//        return request.getMethod().equals(Methods.OPTIONS) && router.getPaths().contains(request.getPath());
//    }
//
//    private boolean hasUnrecognizedMethod(Request request) {
//        return !router.getRecognizedMethods().contains(request.getMethod());
//    }

}
