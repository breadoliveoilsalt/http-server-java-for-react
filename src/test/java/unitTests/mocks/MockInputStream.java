package unitTests.mocks;

import java.io.InputStream;

class MockInputStream extends InputStream {

    @Override
    public int read() {
        return 0;
    }

}
