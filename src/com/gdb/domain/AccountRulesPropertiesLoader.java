
package com.gdb.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AccountRulesPropertiesLoader {

    private Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String filePath) {

        try (InputStream input = new FileInputStream(filePath)) {

            properties.load(input);

            System.out.println(
                    "[Config] Loaded rules from " + filePath
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load properties file: " + filePath,
                    e
            );
        }
    }

    public String getProperty(String key, String defaultValue) {

        return properties.getProperty(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {

        String value = properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value);

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Invalid numeric value for key: " + key,
                    e
            );
        }
    }
}