package unitTests.tests.httpLogic.routerTests;

import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class RouterTests {

    private Router router;
    private Response genericResponse;

//    @Before
//    public void testInit() {
//        buildGenericResponse();
//    }
//
//    private void buildGenericResponse() {
//        genericResponse = new ResponseBuilder()
//                .addStatusCode("200")
//                .addStatusMessage("OK")
//                .addHeader("Date", "Some day")
//                .addHeader("Content-Length", "Some length")
//                .addBody("Some message")
//                .build();
//    }
//
//    private Response returnGenericResponse() {
//        return genericResponse;
//    }
//
//    @Test
//    public void getPathsReturnsASetListingValidPaths() {
//        String path1 = "/simple_get";
//        String path2 = "/simple_post";
//        String path3 = "/simple_delete";
//        buildRouterForGetPathsTest(path1, path2, path3);
//
//        String[] listOfExpectedPaths = {path1, path2, path3};
//        Set<String> expectedPaths = new HashSet<>(Arrays.asList(listOfExpectedPaths));
//
//        assertEquals(expectedPaths, router.getPaths());
//    }
//
//    private void buildRouterForGetPathsTest(String path1, String path2, String path3) {
//        RouterBuilder builder = new RouterBuilder();
//        builder.createPath(path1);
//        builder.createPath(path2);
//        builder.createPath(path3);
//
//        router = builder.build();
//    }
//
//    @Test
//    public void getMethodsForReturnsASetListingValidMethodsForAPath() {
//        String path = "/simple_get";
//        String method1 = "GET";
//        String method2 = "POST";
//        setControllerForGetMethodsForTest(path, method1, method2);
//
//        String[] listOfExpectedMethods = {method1, method2};
//        Set<String> expectedMethods = new HashSet<>(Arrays.asList(listOfExpectedMethods));
//
//        assertEquals(expectedMethods, router.getMethodsFor(path));
//    }
//
//    private void setControllerForGetMethodsForTest(String path, String method1, String method2) {
//        Callable<Response> randomBuildAction = () -> returnGenericResponse();
//        RouterBuilder builder = new RouterBuilder();
//        builder.createPath(path)
//                .addMethodAndAction(method1, randomBuildAction)
//                .addMethodAndAction(method2, randomBuildAction);
//
//        router = builder.build();
//    }
//
//    @Test
//    public void getActionForReturnsTheActionAssociatedWithAPathAndMethod() {
//        String path = "/some_path";
//        String method = "GET";
//        Callable<Response> buildAction = () -> returnGenericResponse();
//        setControllerForGetActionForTest(path, method, buildAction);
//
//        assertEquals(buildAction, router.getActionFor(path, method));
//    }
//
//    private void setControllerForGetActionForTest(String path, String method, Callable<Response> action) {
//        RouterBuilder builder = new RouterBuilder();
//        builder.createPath(path)
//                .addMethodAndAction(method, action);
//        router = builder.build();
//    }

}
