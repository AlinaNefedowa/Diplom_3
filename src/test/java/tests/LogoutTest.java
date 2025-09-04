package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import pages.ProfilePage;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogoutTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Description("Выход из аккаунта")
    void logoutFromProfile() {
        MainPage main = new MainPage(driver);
        main.clickPersonalAccount();

        ProfilePage profile = new ProfilePage(driver);
        profile.logout();

        Assertions.assertTrue(driver.getCurrentUrl().contains("login"));
    }
}
