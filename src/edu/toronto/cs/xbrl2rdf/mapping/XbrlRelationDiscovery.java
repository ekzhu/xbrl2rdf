
package edu.toronto.cs.xbrl2rdf.mapping;

import edu.toronto.cs.xbrl2rdf.config.RunConfig;
import edu.toronto.cs.xcurator.common.DataDocument;
import edu.toronto.cs.xcurator.discoverer.MappingDiscoveryStep;
import edu.toronto.cs.xcurator.mapping.Attribute;
import edu.toronto.cs.xcurator.mapping.Entity;
import edu.toronto.cs.xcurator.mapping.Mapping;
import edu.toronto.cs.xcurator.mapping.Reference;
import edu.toronto.cs.xcurator.mapping.Relation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ekzhu
 */
public class XbrlRelationDiscovery implements MappingDiscoveryStep {
    
    private RunConfig config;
    
    public XbrlRelationDiscovery(RunConfig config) {
        this.config = config;
    }

    @Override
    public void process(List<DataDocument> dataDocuments, Mapping mapping) {
        /**
         * Find entities, needs to be improved for multiple document case when
         * both type names exist.
         */
        Entity contextEntity = mapping.getEntity(config.getTypeResourceUriBase()+ "context");
        if (contextEntity == null) {
            contextEntity = mapping.getEntity(config.getTypeResourceUriBase() + "xbrli-context");
        }
        Entity unitEntity = mapping.getEntity(config.getTypeResourceUriBase() + "unit");
        if (unitEntity == null) {
            unitEntity = mapping.getEntity(config.getTypeResourceUriBase() + "xbrli-unit");
        }
        
        /**
         * Converting attributes in XBRL to relations
         */
        
        Iterator<Entity> iterator = mapping.getEntityIterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            Iterator<Attribute> attrIterator = entity.getAttributeIterator();
            while (attrIterator.hasNext()) {
                Attribute attribute = attrIterator.next();
                // Linking contextRef with actual context entity
                if (attribute.getTypeUri().endsWith("contextRef") && 
                        attribute.getPath().equals("@contextRef")) {
                    // Create a relation to the context entity
                    String path = contextEntity.getPath();
                    Relation contextRel = new Relation(config.getPropertyResourceUriBase()+ "context", 
                            path, contextEntity.getTypeUri());
                    // Add reference to enable search based on id
                    Reference reference = new Reference("@contextRef", "@id");
                    contextRel.addReference(reference);
                    entity.addRelation(contextRel);
                }
                // Linking unitRef with actual unit entity
                if (attribute.getTypeUri().endsWith("unitRef") && 
                        attribute.getPath().equals("@unitRef")) {
                    // Create a relation to the unit entity
                    String path = unitEntity.getPath();
                    Relation unitRel = new Relation(config.getPropertyResourceUriBase() + "unit", 
                            path, unitEntity.getTypeUri());
                    // Add reference to enable search based on id
                    Reference reference = new Reference("@unitRef", "@id");
                    unitRel.addReference(reference);
                    entity.addRelation(unitRel);
                }
            }
        }
    }
    
}
