package edu.toronto.cs.xbrl2rdf.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class RunConfig {
    
    private Configuration config;
    
    final String mappingSerializedDirectoryPath;
    final String entityResourceUriBase;
    final String defaultEntityResourceTypeUri;
    final String defaultEntityResourceTypePrefix;
    final String tdbDirectoryPath;
    
    public RunConfig(){
        try {
            config = new PropertiesConfiguration("setting.properties");
        } catch (ConfigurationException ex) {
            Logger.getLogger(RunConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mappingSerializedDirectoryPath = config.getString("mapping.savedtodirectory");
        entityResourceUriBase = config.getString("rdf.resource.uribase");
        defaultEntityResourceTypeUri = config.getString("rdf.resource.type.default.uri");
        defaultEntityResourceTypePrefix = config.getString("rdf.resource.type.default.prefix");
        tdbDirectoryPath = config.getString("rdf.tdb.directory");
    }

    public String getEntityResourceUriBase() {
        return entityResourceUriBase.endsWith("/") ?
                entityResourceUriBase : entityResourceUriBase + "/";
    }

    public String getDefaultEntityResourceTypeUri() {
        return defaultEntityResourceTypeUri;
    }

    public String getDefaultEntityResourceTypePrefix() {
        return defaultEntityResourceTypePrefix;
    }

    public String getMappingSerializedFilePath(String fileName) {
        return mappingSerializedDirectoryPath.endsWith("/") ? 
                mappingSerializedDirectoryPath + fileName :
                mappingSerializedDirectoryPath + "/" + fileName;
    }
    
    public String getTdbDirectory() {
        return tdbDirectoryPath;
    }
}
