package edu.toronto.cs.xbrl2rdf.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class RunConfig {

    private Configuration config;

    // From setting file
    final String resourceUriBase;
    final String typeResourceUriBase;
    final String defaultTypeResourceUri;
    final String defaultTypeResourcePrefix;
    final String propertyResourceUriBase;
    final String defaultPropertyResourceUri;
    final String defaultPropertyResourcePrefix;

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
        
        defaultTypeResourceUri = this.domain + config.getString("rdf.type.default.uri",
                "/resource/class/default");
        
        defaultTypeResourcePrefix = config.getString("rdf.type.default.prefix",
                "default-type");

        String propertyUriBase = config.getString("rdf.property.uribase",
                "/resource/property/");
        propertyResourceUriBase = this.domain + (propertyUriBase.endsWith("/")
                ? propertyUriBase : propertyUriBase + "/");
        
        defaultPropertyResourceUri = this.domain + config.getString("rdf.property.default.uri",
                "/resource/property/default");
        
        defaultPropertyResourcePrefix = config.getString("rdf.property.default.prefix",
                "default-property");
    }

    public String getResourceUriBase() {
        return resourceUriBase;
    }
    
    public String getTypeResourceUriBase() {
        return typeResourceUriBase;
    }
    
    public String getPropertyResourceUriBase() {
        return propertyResourceUriBase;
    }

    public String getDefaultTypeResourceUri() {
        return domain + defaultTypeResourceUri;
    }

    public String getDefaultTypeResourcePrefix() {
        return defaultTypeResourcePrefix;
    }

    public String getTdbDirectory() {
        return tdbDirectory;
    }

    public void setTdbDirectory(String tdbDirectory) {
        this.tdbDirectory = tdbDirectory;
    }

}
