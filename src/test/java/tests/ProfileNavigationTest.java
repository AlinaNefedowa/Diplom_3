package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import pages.ProfilePage;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileNavigationTest {

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
    @Description("Переход в личный кабинет и обратно в конструктор")
    void profileToConstructorNavigation() {
        MainPage main = new MainPage(driver);
        main.clickPersonalAccount();

        ProfilePage profile = new ProfilePage(driver);
        profile.goToConstructor();
        Assertions.assertTrue(driver.getCurrentUrl().contains("react-burger-constructor"));

        main.clickLogo(); // возвращение на главную через логотип
        Assertions.assertTrue(driver.getCurrentUrl().contains("stellarburgers"));
    }
}

