# Mobile UI Automation Framework

## Project Overview

This project is a mobile automation testing framework developed using Java, Appium, and Cucumber. It automates the Sauce Labs sample mobile application on Android and iOS.

The framework follows the Page Object Model (POM) design pattern and uses Cucumber to write test scenarios in a simple, readable format. A few API tests using Rest Assured are also included.

The main objective is to provide a reusable framework that is easy to maintain and can be extended with more mobile test scenarios.

## Project Status

| Area               | Status                                      |
| ------------------ | ------------------------------------------- |
| Android automation | Completed and executed successfully         |
| iOS automation     | Implementation completed; execution pending |
| API automation     | 3 tests implemented                         |

Android tests were executed on a Pixel 6 emulator running Android 14. iOS execution is pending because I did not have access to a Mac during development.

## Test Scenarios

| No. | Test Scenario                         | Tags                |
| --- | ------------------------------------- | ------------------- |
| 1   | Verify login with valid credentials   | @smoke, @regression |
| 2   | Verify login with invalid credentials | @regression         |
| 3   | Verify logout functionality           | @smoke, @regression |
| 4   | Verify adding a product to the cart   | @regression         |

The Cucumber feature files are in `src/test/resources/features/`.

## Technologies Used

| Component                 | Technology                     |
| ------------------------- | ------------------------------ |
| Programming language      | Java 17                        |
| Build tool                | Maven                          |
| Mobile automation         | Appium 2                       |
| Android automation driver | UiAutomator2                   |
| iOS automation driver     | XCUITest                       |
| BDD framework             | Cucumber                       |
| Test runner               | JUnit 5                        |
| API automation            | Rest Assured                   |
| Reporting                 | Cucumber HTML and JSON reports |
| Design pattern            | Page Object Model              |

## Prerequisites

Install the following tools before running the tests:

- JDK 17 (a full JDK, not only a JRE)
- Maven 3.9 or later (or the Maven support built into Eclipse / IntelliJ IDEA)
- Node.js 18 or later
- Appium 2
- Android Studio and an Android emulator
- Xcode and an iOS simulator (macOS only, for iOS execution)

Install Appium and the drivers:

```bash
npm install -g appium
appium driver install uiautomator2
#appium driver install xcuitest
```

The XCUITest driver and the iOS simulator require macOS.

For Android, set the `ANDROID_HOME` environment variable and add the Android SDK `platform-tools` folder to the system `PATH`. Restart the terminal (and the Appium server) after changing environment variables.

Verify the installation:

```bash
java -version
mvn -version
node -v
appium -v
adb devices
```

The Android emulator should appear in the device list with the status `device`.

## Application Setup

Download the Sauce Labs sample application from the official release page:

https://github.com/saucelabs/sample-app-mobile/releases

Place the files in the `apps/` folder:

- Android: `apps/android.apk`
- iOS simulator build: `apps/ios-simulator.zip`

The application files are not included in the repository. The `app.path` value in `config/android.properties` and `config/ios.properties` must match the actual file name and location.

## Running the Tests

Start the Appium server in a separate terminal and keep it running during the test execution:

```bash
appium
```

### Android Tests

```bash
mvn test -Dplatform=android -Dtest=RunCucumberTest
```

### Smoke Tests

```bash
mvn test -Dplatform=android -Dtest=RunCucumberTest -Dcucumber.filter.tags="@smoke"
```

### Regression Tests

```bash
mvn test -Dplatform=android -Dtest=RunCucumberTest -Dcucumber.filter.tags="@regression"
```

### iOS Tests

Run on a properly configured macOS system:

```bash
mvn test -Dplatform=ios -Dtest=RunCucumberTest
```

The iOS tests have not been executed yet.

### API Tests

```bash
mvn test -Dtest=ApiTest
```

The API tests use a public demo API (jsonplaceholder.typicode.com) for basic request and response validation.

### Running from an IDE

The tests can also be started from Eclipse by running `RunCucumberTest.java` as a JUnit test. To select a platform or tag, add VM arguments such as `-Dplatform=android` or `-Dcucumber.filter.tags=@smoke`.

## Configuration

Settings are kept outside the test code. The framework reads each setting in this order:

1. Command-line property, for example `-Dplatform=ios`
2. Environment variable, for example `PLATFORM=ios`
3. Property file: `config/android.properties` or `config/ios.properties`

| Setting             | Description                                      |
| ------------------- | ------------------------------------------------ |
| `platform`          | `android` (default) or `ios`                     |
| `appium.url`        | Appium server URL                                |
| `device.name`       | Emulator, simulator or device name               |
| `platform.version`  | OS version                                       |
| `app.path`          | Path to the application file                     |
| `app.wait.activity` | Android screen to wait for after the app starts  |
| `implicit.wait`     | Maximum wait time in seconds                     |

For environment variables, use capital letters and replace dots with underscores. For example, `app.path` becomes `APP_PATH`.

## Test Reports

| Output                    | Location                      |
| ------------------------- | ----------------------------- |
| HTML report               | `target/cucumber-report.html` |
| JSON report               | `target/cucumber.json`        |
| Failure screenshots       | Attached to the HTML report   |
| Sample execution evidence | `docs/`                       |

When a scenario fails, a screenshot is captured and attached to the Cucumber report. The report is regenerated on every run, so a later passing run replaces the report of an earlier failed run. A sample failed-run screenshot is kept in `docs/`.

## Framework Structure

```text
config/
    android.properties
    ios.properties

apps/
    (application files, not committed)

docs/
    Sample reports and screenshots

src/test/java/com/assessment/
    drivers/
    pages/
    steps/
    runners/
    utils/
    api/

src/test/resources/features/
    Cucumber feature files
```

- **drivers:** Creates the Appium driver for the selected platform.
- **pages:** Page classes and element locators.
- **steps:** Cucumber step definitions and hooks (session setup, failure screenshot, cleanup).
- **runners:** Cucumber test runner.
- **utils:** Configuration reader.
- **api:** API tests.
- **features:** Test scenarios written in Gherkin.
- **config:** Platform-specific settings.
- **docs:** Sample reports and screenshots.

## Framework Design

| Decision | Reason |
| -------- | ------ |
| Page Object Model | Keeps locators and page actions separate from the test steps, so UI changes are easy to update. |
| One `DriverFactory` | It is the only class that knows the difference between Android and iOS driver setup. |
| Android and iOS locators in the same page class | Each element has both an Android and an iOS locator, so the steps and feature files are the same for both platforms. |
| Accessibility IDs first | They work on both platforms and are more stable than XPath. XPath or iOS class chain is used only where an element has no usable accessibility ID (error message, cart badge). |
| Cucumber with tags | Scenarios are readable, and `@smoke` and `@regression` allow running groups of tests separately. |
| New session per scenario | Each scenario starts with a clean app, so tests do not depend on each other. The trade-off is a longer execution time. |
| Settings outside the code | The same tests can run on a different device or platform without code changes. |

## Test Reliability and Failure Handling

- **Explicit waits** are used for elements and application states. There are no fixed sleeps.
- **Locator strategy:** accessibility IDs are preferred to reduce locator-related failures.
- **Test isolation:** a fresh application session is started for each scenario.
- **Application startup:** the Android startup wait was configured (`app.wait.activity`) to handle the splash screen transition.
- **Failure screenshots:** a screenshot is captured for each failed scenario and attached to the report. The scenario name is also logged.
- **Reports:** Cucumber reports are used to review results and investigate failures.
- **No automatic retry:** retries are not enabled, so genuine failures are not hidden.
- **API tests** depend on a public demo service. A failure should be checked against the service response and network availability before treating it as a defect.

## Challenges Faced and Solutions

| Issue | Cause | Solution |
| ----- | ----- | -------- |
| `NoClassDefFoundError: ContextAware` | Maven downloaded Selenium 4.51.0, which is not compatible with the Appium Java client used here (9.3.0). | Pinned the Selenium version to 4.23.1 in `pom.xml`. |
| `ANDROID_HOME` was not set | The terminal that started Appium did not have the environment variable. | Set `ANDROID_HOME`, updated `PATH`, and restarted the terminal and the Appium server. |
| `SplashActivity never started` | The application opened manually, but Appium missed the splash screen because it closes very quickly. | Configured the wait activity (`app.wait.activity`) and increased the launch timeouts. |
| Failure screenshot not visible in the report | A later passing run replaced the report of the failed run. | Re-ran the failing scenario and captured the report straight away. A sample is kept in `docs/`. |
| `adb devices` showed no device | The emulator had not finished starting. | Started the emulator, waited for the home screen, and checked `adb devices` again. |

The dependency versions and environment variables should be checked when setting up the framework on another machine.

## How AI Was Used

I used an AI assistant this assessment. I made the final decisions, ran everything on my own machine, and reviewed the changes before using them.

- **Planning:** compared tool options and agreed the framework structure (Java, Cucumber, JUnit 5, Appium PageFactory, one `DriverFactory` for both platforms).
- **Troubleshooting:** shared real error logs to find the cause of each issue listed above. I applied and tested every fix myself.

## Current Limitations

- **iOS execution is pending:** the iOS driver setup and locators are implemented but have not been run on a Mac or iOS simulator. Some locators may need adjustment after the first run.
- **Limited coverage:** four mobile scenarios, executed on one emulator configuration (Pixel 6, Android 14).
- **Execution time:** a new session for every scenario increases the total run time.
- **External API dependency:** API tests use a public demo service, so network problems or service availability can affect the results.
- **Device compatibility:** other Android versions, physical devices and iOS simulators still need validation.

## Sample Execution Evidence

The `docs/` folder contains screenshots from the test runs:

| File                              | Description                                        |
| --------------------------------- | -------------------------------------------------- |
| `android-test-report1.png`        | Android test report (passing run)                  |
| `android-test-report2.png`        | Android test report (passing run, other features)  |
| `android-console-output.png`      | Console output of a passing run                    |
| `smoke-run-login-and-logout.png`  | Smoke run (`@smoke`: login and logout scenarios)   |
| `failure-screenshot1-proof.png`   | Failed scenario with its screenshot in the report  |
| `failure-screenshot2-proof.png`   | Failed scenario with its screenshot in the report  |
| `console-failure-screenshot.png`  | Console output of the failed run                   |
| `api-tests.png`                   | API test execution results                         |
