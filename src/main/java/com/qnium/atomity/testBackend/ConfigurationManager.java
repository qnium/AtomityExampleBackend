/*
 * This file is copyrighted by QNIUM LLC
 * And licensed on eclusive/non-exclusive basis under the terms of the license given to the final user 
 * or in terms of the contract concluded between sources licensee and QNIUM LLC
 */
package com.qnium.atomity.testBackend;

import javax.servlet.ServletContext;

/**
 *
 * @author nbv
 */
public class ConfigurationManager {

    private ServletContext _config;
    
    private ConfigurationManager() {
    }
    
    public static ConfigurationManager getInstance() {
        return ConfigurationManagerHolder.INSTANCE;
    }

    void initConfig(ServletContext config) {
        _config = config;
        String param;       
    }
    
    private static class ConfigurationManagerHolder {

        private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    }
}
