package com.assessment.drivers;

import com.assessment.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/** Single place where Android vs iOS differences live. */
public final class DriverFactory {
    private DriverFactory() {}

    public static AppiumDriver create() {
        try {
            URL server = new URL(ConfigReader.get("appium.url"));
            String app = new File(ConfigReader.get("app.path")).getAbsolutePath();

            if (ConfigReader.isAndroid()) {
                UiAutomator2Options o = new UiAutomator2Options()
                        .setDeviceName(ConfigReader.get("device.name"))
                        .setPlatformVersion(ConfigReader.get("platform.version"))
                        .setApp(app)
                        .setAutoGrantPermissions(true)
                        .setAppWaitActivity(ConfigReader.get("app.wait.activity"))
                        .setAppWaitDuration(Duration.ofSeconds(60))
                        .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(90))
                        .setAdbExecTimeout(Duration.ofSeconds(60))
                        .setNewCommandTimeout(Duration.ofSeconds(120));
                return new AndroidDriver(server, o);
            }
            XCUITestOptions o = new XCUITestOptions()
                    .setDeviceName(ConfigReader.get("device.name"))
                    .setPlatformVersion(ConfigReader.get("platform.version"))
                    .setApp(app)
                    .setWdaLaunchTimeout(Duration.ofSeconds(120))
                    .setNewCommandTimeout(Duration.ofSeconds(120));
            return new IOSDriver(server, o);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid appium.url", e);
        }
    }
}
