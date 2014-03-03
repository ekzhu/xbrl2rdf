package edu.toronto.cs.xbrl2rdf.config;

import edu.toronto.cs.xcurator.rdf.RdfConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class RunConfig implements RdfConfig {

    private Configuration config;

    // From setting file
    final String resourceUriBase;
    final String typeResourceUriBase;
    final String typeResourcePrefix;
    final String propertyResourceUriBase;
    final String propertyResourcePrefix;

    // From run time setting
    String domain;
    String tdbDirectory;

    public RunConfig(String domain) {
        try {
            config = new PropertiesConfiguration("setting.properties");
        } catch (ConfigurationException ex) {
            Logger.getLogger(RunConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        // We have to check if this domain is a valid uri before initializing
        // the config
        this.domain = domain.endsWith("/") ? 
                domain.substring(0, domain.length()-1) :
                domain;
        
        resourceUriBase = this.domain + config.getString("rdf.uribase", "/resource/");

        String typeUriBase = config.getString("rdf.type.uribase",
                "/resource/class/");
        typeResourceUriBase = this.domain + (typeUriBase.endsWith("/")
                ? typeUriBase : typeUriBase + "/");
        
        typeResourcePrefix = config.getString("rdf.type.prefix",
                "class");

        String propertyUriBase = config.getString("rdf.property.uribase",
                "/resource/property/");
        propertyResourceUriBase = this.domain + (propertyUriBase.endsWith("/")
                ? propertyUriBase : propertyUriBase + "/");
        
        propertyResourcePrefix = config.getString("rdf.property.prefix",
                "property");
    }

    @Override
    public String getResourceUriBase() {
        return resourceUriBase;
    }
    
    @Override
    public String getTypeResourceUriBase() {
        return typeResourceUriBase;
    }
    
    @Override
    public String getPropertyResourceUriBase() {
        return propertyResourceUriBase;
    }

    @Override
    public String getTypeResourcePrefix() {
        return typeResourcePrefix;
    }
    
    @Override
    public String getPropertyResourcePrefix() {
        return propertyResourcePrefix;
    }

}
