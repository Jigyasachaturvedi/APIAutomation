package com.api.utils;
import java.io.*;
import java.util.Properties;


public class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static void saveProperties() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Updated Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
