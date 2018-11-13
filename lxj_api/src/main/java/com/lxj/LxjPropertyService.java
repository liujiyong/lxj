/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lxj;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 *
 * @author admin
 */
@Configuration
public class LxjPropertyService {

    private Properties properties;

    private static final Logger LOGGER = LogManager.getLogger(LxjPropertyService.class);

    @PostConstruct
    public void init() {

        try {
            properties = PropertiesLoaderUtils.loadAllProperties("applicationconfig/lxj.properties");
        } catch (IOException e) {

            LOGGER.error(e.getMessage());
        }

    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

}
