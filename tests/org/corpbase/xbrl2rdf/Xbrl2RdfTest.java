
package org.corpbase.xbrl2rdf;

import edu.toronto.cs.xbrl2rdf.Xbrl2Rdf;
import java.io.File;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author ekzhu
 */
public class Xbrl2RdfTest {
    
    private final String domain = "http://corpbase.org/";
    private String testTdbDir;
    
    @Rule
    public TemporaryFolder testTdbFolder = new TemporaryFolder();
    
    @Before
    public void setup() {
        File testTdb = testTdbFolder.newFolder("testTdb");
        testTdbDir = testTdb.getAbsolutePath();
    }
    
    @Test
    public void testMain() {
        String[] args = new String[] {"-d", "resources/data/fb-20121231.xml",
            "resources/data/fb-20131231.xml",
        "-t", testTdbDir, "-m", "mapping.xml", "-h", domain};
        Xbrl2Rdf.main(args);
    }
}
