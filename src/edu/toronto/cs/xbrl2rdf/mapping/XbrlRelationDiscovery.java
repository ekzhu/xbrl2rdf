
package edu.toronto.cs.xbrl2rdf.mapping;

import edu.toronto.cs.xcurator.discoverer.DataDocument;
import edu.toronto.cs.xcurator.discoverer.MappingDiscoveryStep;
import edu.toronto.cs.xcurator.mapping.Attribute;
import edu.toronto.cs.xcurator.mapping.Entity;
import edu.toronto.cs.xcurator.mapping.Mapping;
import edu.toronto.cs.xcurator.mapping.Reference;
import edu.toronto.cs.xcurator.mapping.Relation;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ekzhu
 */
public class XbrlRelationDiscovery implements MappingDiscoveryStep {

    @Override
    public void process(List<DataDocument> dataDocuments, Mapping mapping) {
        /**
         * Converting attributes in XBRL to relations
         */
        Entity contextEntity = mapping.getEntity("http://www.xbrl.org/2003/instance#context");
        Entity unitEntity = mapping.getEntity("http://www.xbrl.org/2003/instance#unit");
        
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
                    Relation contextRel = new Relation(entity.getTypeUri() + ".context", 
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
                    Relation unitRel = new Relation(entity.getTypeUri() + ".unit", 
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
