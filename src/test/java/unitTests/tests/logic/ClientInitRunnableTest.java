package unitTests.tests.logic;

import httpServer.logic.ClientInitRunnable;
import unitTests.factoryForTests.MockAppFactory;
import unitTests.mocks.MockSokket;

import org.junit.Before;
import org.junit.Test;

public class ClientInitRunnableTest {

    private MockSokket sokket;
    private MockAppFactory factory;
    private ClientInitRunnable clientInitRunnable;

    @Before
    public void initTests() {
        sokket = new MockSokket();
        clientInitRunnable = new ClientInitRunnable(sokket, factory);
    }

//    @Test
    // add tests!

}
