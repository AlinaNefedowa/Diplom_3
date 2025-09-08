package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import pages.ConstructorPage;
import utils.BrowserManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstructorTest {

    private WebDriver driver;
    private ConstructorPage constructorPage;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        String browserName = testInfo.getDisplayName().toLowerCase().contains("yandex") ? "yandex" : "chrome";
        driver = BrowserManager.getDriver(browserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/");
        constructorPage = new ConstructorPage(driver);
    }

    @AfterEach
    public void teardown() {
        BrowserManager.quitDriver(driver);
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка перехода к разделу 'Соусы'")
    @Description("Переход по клику на таб 'Соусы'")
    public void goToSaucesSectionTest(String browserName) {
        step("Нажать на таб 'Соусы'");
        constructorPage.goToSauces();

        step("Проверить, что заголовок 'Соусы' отображается");
        assertTrue(constructorPage.isSaucesTitleDisplayed());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка перехода к разделу 'Начинки'")
    @Description("Переход по клику на таб 'Начинки'")
    public void goToFillingsSectionTest(String browserName) {
        step("Нажать на таб 'Начинки'");
        constructorPage.goToFillings();

        step("Проверить, что заголовок 'Начинки' отображается");
        assertTrue(constructorPage.isFillingsTitleDisplayed());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка перехода к разделу 'Булки'")
    @Description("Переход по клику на таб 'Булки'")
    public void goToBunsSectionTest(String browserName) {
        step("Нажать на таб 'Начинки' для перехода на другой раздел");
        constructorPage.goToFillings();

        step("Нажать на таб 'Булки'");
        constructorPage.goToBuns();

        step("Проверить, что заголовок 'Булки' отображается");
        assertTrue(constructorPage.isBunsTitleDisplayed());
    }
}