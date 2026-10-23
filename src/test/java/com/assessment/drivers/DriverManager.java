package com.assessment.drivers;

import io.appium.java_client.AppiumDriver;

/** ThreadLocal holder so tests can be parallelised later. */
public final class DriverManager {
    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {}

    public static void start() { DRIVER.set(DriverFactory.create()); }

    public static AppiumDriver get() {
        if (DRIVER.get() == null) throw new IllegalStateException("Driver not started");
        return DRIVER.get();
    }

    public static boolean isStarted() { return DRIVER.get() != null; }

    public static void quit() {
        if (DRIVER.get() != null) {
            try { DRIVER.get().quit(); } finally { DRIVER.remove(); }
        }
    }
}
