package unitTests.tests.logic;

import httpServer.logic.ClientHandlerRunnable;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.MockSokket;

import org.junit.Before;

public class ClientHandlerRunnableTest {

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
