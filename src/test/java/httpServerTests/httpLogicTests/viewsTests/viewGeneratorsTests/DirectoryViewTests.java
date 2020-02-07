package httpServerTests.httpLogicTests.viewsTests.viewGeneratorsTests;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.constants.HTTPMethods;
import httpServer.httpLogic.requests.Request;
import httpServer.httpLogic.requests.RequestBuilder;
import httpServer.httpLogic.views.viewGenerators.DirectoryView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DirectoryViewTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void renderGeneratesAStringWithAnUnorderedList() throws IOException {
        Request request = new RequestBuilder().addMethod(HTTPMethods.GET).addPath(tempFolder.getRoot().getPath()).build();
        File directoryFile = tempFolder.newFolder("tempDirectory");

        String result = new DirectoryView(request, directoryFile).render();

        String expectedResult =
                "<ul></ul>";

        assertEquals(expectedResult, result);
    }

}
