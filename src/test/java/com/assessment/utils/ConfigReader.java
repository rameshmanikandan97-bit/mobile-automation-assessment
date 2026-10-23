package com.assessment.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Config-driven execution. Priority: -D system property > ENV variable > config/<platform>.properties
 * e.g. mvn test -Dplatform=ios -Dapp.path=/path/app.zip   OR   APP_PATH=/path/app.zip
 */
public final class ConfigReader {
    private static final Properties PROPS = new Properties();

    static {
        String file = "config/" + platform() + ".properties";
        try (FileInputStream in = new FileInputStream(file)) {
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load config file: " + file, e);
        }
    }

    private ConfigReader() {}

    public static String platform() {
        String p = System.getProperty("platform", System.getenv("PLATFORM"));
        return (p == null || p.isBlank()) ? "android" : p.toLowerCase();
    }

    public static String get(String key) {
        String v = System.getProperty(key);
        if (v == null || v.isBlank()) v = System.getenv(key.toUpperCase().replace('.', '_'));
        if (v == null || v.isBlank()) v = PROPS.getProperty(key);
        if (v == null) throw new IllegalStateException("Missing config key: " + key);
        return v;
    }

    public static boolean isAndroid() {
        return "android".equals(platform());
    }
}
