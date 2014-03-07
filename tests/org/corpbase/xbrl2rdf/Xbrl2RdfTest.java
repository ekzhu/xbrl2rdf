
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
    public void testMain_multiple_documents() {
        String[] args = new String[] {"-d", "resources/data/msft-20130630.xml",
            "resources/data/fb-20131231.xml",
        "-t", testTdbDir, "-m", "mapping.xml", "-h", domain};
        Xbrl2Rdf.main(args);
    }
    
    @Test
    public void testMain_fb() {
        String[] args = new String[] {"-d", "resources/data/fb-20131231.xml",
        "-t", testTdbDir, "-m", "mapping.xml", "-h", domain};
        Xbrl2Rdf.main(args);
    }
    
    @Test
    public void testMain_msft() {
        String[] args = new String[] {"-d", "resources/data/msft-20130630.xml",
        "-t", testTdbDir, "-m", "mapping.xml", "-h", domain};
        Xbrl2Rdf.main(args);
    }
    
    @Test
    public void testMain_fb_remote() {
        String[] args = new String[] {"-d", "http://www.sec.gov/Archives/edgar/data/1326801/000132680114000007/fb-20131231.xml",
        "-t", testTdbDir, "-m", "mapping.xml", "-h", domain};
        Xbrl2Rdf.main(args);
    }
    
    @Test
    public void testMain_aapl_remote() {
        String[] args = new String[] {"-d", "http://edgar.sec.gov/Archives/edgar/data/320193/000119312513416534/aapl-20130928.xml",
        "-t", "tdb", "-h", domain};
        Xbrl2Rdf.main(args);
    }
}
