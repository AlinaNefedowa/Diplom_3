package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.MainPage;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

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
    @Description("Вход через кнопку 'Войти в аккаунт' на главной")
    void loginFromMainButton() {
        MainPage main = new MainPage(driver);
        main.clickLogin();

        LoginPage login = new LoginPage(driver);
        login.fillLoginForm("test@example.com", "password123");
        login.clickLogin();

        Assertions.assertTrue(driver.getCurrentUrl().contains("profile"));
    }

    @Test
    @Description("Вход через кнопку 'Личный кабинет'")
    void loginFromPersonalAccountButton() {
        MainPage main = new MainPage(driver);
        main.clickPersonalAccount();

        LoginPage login = new LoginPage(driver);
        login.fillLoginForm("test@example.com", "password123");
        login.clickLogin();

        Assertions.assertTrue(driver.getCurrentUrl().contains("profile"));
    }
}

