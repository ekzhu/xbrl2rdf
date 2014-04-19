
package org.corpbase.xbrl2rdf.mapping;

import edu.toronto.cs.xbrl2rdf.mapping.MappingFactory;
import edu.toronto.cs.xcurator.mapping.Entity;
import edu.toronto.cs.xcurator.mapping.Mapping;
import edu.toronto.cs.xcurator.mapping.Relation;
import edu.toronto.cs.xcurator.common.XPathFinder;
import edu.toronto.cs.xcurator.common.XmlParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import edu.toronto.cs.xbrl2rdf.config.RunConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ekzhu
 */
public class MappingFactoryTest {
    
    private RunConfig config;
    private XmlParser parser;
    private XPathFinder xpath;
    private String domain = "http://corpbase.org/";
    
    @Before
    public void setup() throws Exception {
        config = new RunConfig(domain);
        parser = new XmlParser();
        xpath = new XPathFinder();
    }
    
    @Test
    public void test_mappingFactory_fb() throws SAXException, IOException, 
            ParserConfigurationException, TransformerConfigurationException, XPathExpressionException {
        MappingFactory factory = new MappingFactory(config);
        
        Document dataDoc = parser.parse(MappingFactoryTest.class.getResourceAsStream(
              "/data/fb-20131231.xml"), -1);
        List<Document> docList = new ArrayList<>();
        docList.add(dataDoc);
        
        // Test
        Mapping mapping = factory.createInstance(docList, "fb-20131231-mapping.xml");
        
        // Verify
        verify(mapping, dataDoc);
    }
    
    @Test
    public void test_mappingFactory_msft() throws SAXException, IOException, 
            ParserConfigurationException, TransformerConfigurationException, XPathExpressionException {
        MappingFactory factory = new MappingFactory(config);
        
        Document dataDoc = parser.parse(MappingFactoryTest.class.getResourceAsStream(
              "/data/msft-20130630.xml"), -1);
        List<Document> docList = new ArrayList<>();
        docList.add(dataDoc);
        
        // Test
        Mapping mapping = factory.createInstance(docList, "msft-20130630-mapping.xml");
        
        // Verify
        verify(mapping, dataDoc);
        
    }
    
    private void verify(Mapping mapping, Document dataDoc) throws XPathExpressionException {
        Entity e = mapping.getEntity("http://fasb.org/us-gaap/2013-01-31/NonoperatingIncomeExpense");
        Assert.assertTrue(e.hasRelation("http://fasb.org/us-gaap/2013-01-31/NonoperatingIncomeExpense"+
                ".http://www.xbrl.org/2003/instance/unit"));
        String contextRelation = "http://fasb.org/us-gaap/2013-01-31/NonoperatingIncomeExpense"+
                ".http://www.xbrl.org/2003/instance/context";
        Assert.assertTrue(e.hasRelation(contextRelation));
        Relation r = e.getRelation(contextRelation);
        
        NodeList nl = xpath.getNodesByPath(e.getPath(), dataDoc, e.getNamespaceContext());
        Assert.assertTrue(nl.getLength() > 0);
        
        Element element = (Element)nl.item(0);
        
        NodeList nl2 = xpath.getNodesByPath(r.getPath(), element, e.getNamespaceContext());
        Assert.assertTrue(nl2.getLength() > 0);
        
        Element contextElement = (Element)nl2.item(0);
        
        Assert.assertTrue(contextElement.getLocalName().equals("context"));
    }
}
