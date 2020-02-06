package httpServerTests.httpLogicTests.viewsTests;

import httpServer.httpLogic.constants.HTTPContentTypes;
import httpServer.httpLogic.views.DirectoryView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class DirectoryViewTests {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void generateHTMLFileGeneratesAnHTMLFile() throws IOException {
       File directoryFile = tempFolder.newFolder("tempDirectory");

       File result = new DirectoryView(directoryFile).generateHTMLFile();

       assertEquals(HTTPContentTypes.TextHTML, Files.probeContentType(result.toPath()));
    }
}
