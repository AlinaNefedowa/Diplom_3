// В папке src/test/java/tests
package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; // Import Test
import org.junit.jupiter.params.ParameterizedTest; // Import ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource; // Import ValueSource
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;
import pages.LoginPage;
import pages.ProfilePage;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected MainPage mainPage;
    protected LoginPage loginPage;
    protected ProfilePage profilePage;

    @BeforeEach
    public void setUp() {

        String browserName = System.getProperty("browser", "chrome");
        System.out.println("--- Setting up driver for: " + browserName.toUpperCase() + " ---");

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                driver = new ChromeDriver(chromeOptions);
                break;
            case "yandex":

                String yandexBrowserPath = "/Applications/Yandex.app/Contents/MacOS/Yandex";

                WebDriverManager.chromedriver().setup();
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary(yandexBrowserPath);
                driver = new ChromeDriver(yandexOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName + ". Please use 'chrome' or 'yandex'.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/");

        // Инициализация Page Objects
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}