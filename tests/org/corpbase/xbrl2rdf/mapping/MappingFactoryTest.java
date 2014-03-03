
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
    public void setup() {
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
        Mapping mapping = factory.createInstance(docList, "fb-20121231-mapping.xml");
        
        // Verify
        Entity e = mapping.getEntity("http://fasb.org/us-gaap/2013-01-31/NonoperatingIncomeExpense");
        Assert.assertTrue(e.hasRelation("http://corpbase.org/resource/property/unit"));
        Assert.assertTrue(e.hasRelation("http://corpbase.org/resource/property/context"));
        Relation r = e.getRelation("http://corpbase.org/resource/property/context");
        
        NodeList nl = xpath.getNodesByPath(e.getPath(), dataDoc, e.getNamespaceContext());
        Assert.assertTrue(nl.getLength() > 0);
        
        Element element = (Element)nl.item(0);
        
        NodeList nl2 = xpath.getNodesByPath(r.getPath(), element, e.getNamespaceContext());
        Assert.assertTrue(nl2.getLength() > 0);
        
        Element contextElement = (Element)nl2.item(0);
        
        Assert.assertTrue(contextElement.getLocalName().equals("context"));
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
        Entity e = mapping.getEntity("http://fasb.org/us-gaap/2013-01-31/NonoperatingIncomeExpense");
        Assert.assertTrue(e.hasRelation("http://corpbase.org/resource/property/unit"));
        Assert.assertTrue(e.hasRelation("http://corpbase.org/resource/property/context"));
        Relation r = e.getRelation("http://corpbase.org/resource/property/context");
        
        NodeList nl = xpath.getNodesByPath(e.getPath(), dataDoc, e.getNamespaceContext());
        Assert.assertTrue(nl.getLength() > 0);
        
        Element element = (Element)nl.item(0);
        
        NodeList nl2 = xpath.getNodesByPath(r.getPath(), element, e.getNamespaceContext());
        Assert.assertTrue(nl2.getLength() > 0);
        
        Element contextElement = (Element)nl2.item(0);
        
        Assert.assertTrue(contextElement.getLocalName().equals("context"));
    }
}
