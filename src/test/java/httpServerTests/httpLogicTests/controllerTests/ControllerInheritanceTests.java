package httpServerTests.httpLogicTests.controllerTests;

import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.responses.Response;
import httpServer.httpLogic.responses.ResponseBuilder;
import httpServer.httpLogic.router.Router;
import httpServer.httpLogic.router.RouterBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ControllerInheritanceTests {

    Router router;
    Request request;

    @Before
    public void testInit() {
        PathOneTestController.getResponseToReturn = new ResponseBuilder()
                            .addBody("Hello World!")
                            .addHeader("TestField", "TestValue")
                            .finalizeMetaDataForOKResponse()
                            .build();
        router = new RouterBuilder().build();
        request = new RequestBuilder().build();
    }

    @Test
    public void extendingControllerGivesASubclassTheAbilityToRespondToHEADRequestsWithoutDefiningAHeadMethodForTheSubclass() {
        Response response = new PathOneTestController(router, request).head();

        assertTrue(response.getStatusCode() == "200");
        assertTrue(response.hasHeader("TestField", "TestValue"));
        assertNull(response.getBody());
    }

    @Test
    public void extendingControllerGivesASubclassTheAbilityToRespondToOPTIONSRequestsWithoutDefiningAnOptionsMethodForTheSubclass() {
        Response response = new PathOneTestController(router, request).options();

        String listOfMethods = response.getHeaderValueFor("Allow");

        assertTrue(response.getStatusCode() == "200");
        assertTrue(listOfMethods.equals("HEAD, GET, OPTIONS"));
        assertNull(response.getBody());
    }

}
