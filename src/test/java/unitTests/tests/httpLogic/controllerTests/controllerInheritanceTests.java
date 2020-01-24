package unitTests.tests.httpLogic.controllerTests;

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

public class controllerInheritanceTests {

    Router router;
    Request request;

    @Before
    public void testInit() {
        TestController.getResponseToReturn = new ResponseBuilder()
                            .addBody("Hello World!")
                            .addHeader("TestField", "TestValue")
                            .finalizeMetaDataForOKResponse()
                            .build();
        router = new RouterBuilder().build();
        request = new RequestBuilder().build();
    }

    @Test
    public void extendingControllerGivesASubclassTheAbilityToRespondToHEADRequestsWithoutDefiningAHeadMethodForTheSubclass() {
        Response response = new TestController(router, request).head();

        assertTrue(response.getStatusCode() == "200");
        assertTrue(response.hasHeader("TestField", "TestValue"));
        assertNull(response.getBody());
    }

    @Test
    public void extendingControllerGivesASubclassTheAbilityToRespondToOPTIONSRequestsWithoutDefiningAnOptionsMethodForTheSubclass() {
        Response response = new TestController(router, request).options();

        String listOfMethods = response.getHeaderValueFor("Allow");

        assertTrue(response.getStatusCode() == "200");
        assertTrue(listOfMethods.equals("HEAD, GET, OPTIONS"));
        assertNull(response.getBody());
    }

}