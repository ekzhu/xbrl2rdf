package edu.toronto.cs.xbrl2rdf;

import edu.toronto.cs.xcurator.mapping.Mapping;
import edu.toronto.cs.xcurator.rdf.RdfGeneration;
import edu.toronto.cs.xcurator.rdf.RdfGenerator;
import edu.toronto.cs.xcurator.xml.ElementIdGenerator;
import edu.toronto.cs.xcurator.xml.XPathFinder;
import edu.toronto.cs.xbrl2rdf.config.RunConfig;
import edu.toronto.cs.xbrl2rdf.mapping.MappingFactory;
import org.w3c.dom.Document;

public class RdfFactory {
    
    private final MappingFactory mappingFactory;
    private final RunConfig config;
    private final XPathFinder xpath;
    private final ElementIdGenerator idGenerator;
    
    public RdfFactory(RunConfig config) {
        this.config = config;
        mappingFactory = new MappingFactory(this.config);
        xpath = new XPathFinder();
        idGenerator = new ElementIdGenerator();
    }
    
    /**
     * Generate RDFs from the XBRL document
     * @param xbrlDocument
     * @param tdbDirectory
     */
    public void createRdfs(Document xbrlDocument, String tdbDirectory) {
        
        config.setTdbDirectory(tdbDirectory);
        
        // Create a entity mapping from the XBRL document
        Mapping mapping = mappingFactory.createInstance(xbrlDocument);
        
        RdfGenerator rdfGenerator = new RdfGenerator(xbrlDocument, mapping);
        RdfGeneration rdfGenerationStep = new RdfGeneration(config.getTdbDirectory()
                , xpath, idGenerator);
        rdfGenerator.addStep(rdfGenerationStep);
        rdfGenerator.generateRdfs();
    }
}
