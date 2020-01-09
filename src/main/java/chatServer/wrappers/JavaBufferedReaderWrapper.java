package chatServer.wrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JavaBufferedReaderWrapper implements Reader {

    private final BufferedReader reader;

    public JavaBufferedReaderWrapper(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

}
