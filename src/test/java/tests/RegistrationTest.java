package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import pages.RegistrationPage;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver(); // Для Яндекс.Браузера можно использовать Edge/Chromium драйвер
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Description("Проверка успешной регистрации")
    void successfulRegistration() {
        MainPage main = new MainPage(driver);
        main.clickLogin();

        RegistrationPage reg = new RegistrationPage(driver);
        String email = "user" + System.currentTimeMillis() + "@example.com";
        reg.fillRegistrationForm("TestUser", email, "password123");
        reg.clickRegister();

        Assertions.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    @Test
    @Description("Проверка ошибки при слишком коротком пароле")
    void shortPasswordError() {
        MainPage main = new MainPage(driver);
        main.clickLogin();

        RegistrationPage reg = new RegistrationPage(driver);
        reg.fillRegistrationForm("TestUser", "test@example.com", "123");
        reg.clickRegister();

        Assertions.assertEquals("Некорректный пароль", reg.getErrorText());
    }
}

