package unitTests.tests.httpLogic;

import httpServer.httpLogic.ClientHandlerRunnable;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.MockSokket;

import org.junit.Before;

public class ClientHandlerRunnableTests {

    private MockSokket sokket;
    private MockAppFactory factory;
    private ClientHandlerRunnable clientHandlerRunnable;

    @Before
    public void initTests() {
        sokket = new MockSokket();
        clientHandlerRunnable = new ClientHandlerRunnable(sokket, factory);
    }

//    @Test
    // add tests!

}
