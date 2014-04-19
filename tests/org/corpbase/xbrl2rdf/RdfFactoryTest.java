package org.corpbase.xbrl2rdf;

import edu.toronto.cs.xbrl2rdf.RdfFactory;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;
import edu.toronto.cs.xcurator.common.XmlParser;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import edu.toronto.cs.xbrl2rdf.config.RunConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author ekzhu
 */
public class RdfFactoryTest {

    private String testTdbDir;
    private XmlParser parser;
    private final String domain = "http://corpbase.org/";

    @Rule
    public TemporaryFolder testTdbFolder = new TemporaryFolder();

    @Before
    public void setup() throws IOException {
        File testTdb = testTdbFolder.newFolder("testTdb");
        testTdbDir = testTdb.getAbsolutePath();
        parser = new XmlParser();
    }

    @Test
    public void test_rdfcreation_fb() throws SAXException, IOException, ParserConfigurationException, Exception {

        Document dataDoc = parser.parse(RdfFactoryTest.class.getResourceAsStream(
                "/data/fb-20121231.xml"), -1);

        RdfFactory factory = new RdfFactory(new RunConfig(domain));
        factory.createRdfs(dataDoc, testTdbDir);

        Dataset dataset = TDBFactory.createDataset(testTdbDir);
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();
        Assert.assertFalse("No RDF was generated. TDB directory: " + testTdbDir, model.isEmpty());

        dataset.end();
    }
    
    @Test
    public void test_rdfcreation_msft() throws SAXException, IOException, ParserConfigurationException, Exception {

        Document dataDoc = parser.parse(RdfFactoryTest.class.getResourceAsStream(
                "/data/msft-20130630.xml"), -1);

        RdfFactory factory = new RdfFactory(new RunConfig(domain));
        factory.createRdfs(dataDoc, testTdbDir);

        Dataset dataset = TDBFactory.createDataset(testTdbDir);
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();
        Assert.assertFalse("No RDF was generated. TDB directory: " + testTdbDir, model.isEmpty());

        dataset.end();
    }
}
