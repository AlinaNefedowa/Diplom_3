package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ConstructorPage;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstructorTest {

    private WebDriver driver;
    private MainPage mainPage;
    private ConstructorPage constructorPage;

    @BeforeAll
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage(driver);
        constructorPage = new ConstructorPage(driver);
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Соусы'")
    @Description("Переход по клику на таб 'Соусы'")
    public void goToSaucesSectionTest() {
        step("Нажать на таб 'Соусы'");
        constructorPage.goToSauces();

        step("Проверить, что заголовок 'Соусы' отображается");
        assertTrue(constructorPage.isSaucesTitleDisplayed());
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Начинки'")
    @Description("Переход по клику на таб 'Начинки'")
    public void goToFillingsSectionTest() {
        step("Нажать на таб 'Начинки'");
        constructorPage.goToFillings();

        step("Проверить, что заголовок 'Начинки' отображается");
        assertTrue(constructorPage.isFillingsTitleDisplayed());
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Булки'")
    @Description("Переход по клику на таб 'Булки'")
    public void goToBunsSectionTest() {
        step("Нажать на таб 'Начинки' для перехода на другой раздел");
        constructorPage.goToFillings();

        step("Нажать на таб 'Булки'");
        constructorPage.goToBuns();

        step("Проверить, что заголовок 'Булки' отображается");
        assertTrue(constructorPage.isBunsTitleDisplayed());
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}