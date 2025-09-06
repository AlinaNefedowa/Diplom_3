package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class BrowserManager {

    public static WebDriver getDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                chromeOptions.setExperimentalOption("prefs", new HashMap<String, Object>() {{
                    put("profile.password_manager_leak_detection", false);
                    put("profile.password_manager_enabled", false);
                }});
                chromeOptions.addArguments("--disable-features=PasswordManagerEnabled,PasswordLeakDetection");
                return new ChromeDriver(chromeOptions);
            case "yandex":
                String yandexBrowserPath = "/Applications/Yandex.app/Contents/MacOS/Yandex";

                System.setProperty("webdriver.chrome.driver", "/Users/alinanefedowa/yandexdriver");
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary(yandexBrowserPath);

                return new ChromeDriver(yandexOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }
}