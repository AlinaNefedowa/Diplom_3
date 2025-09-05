package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для табов "Конструктора"
    public static final By BUNS_TAB = By.xpath("//span[text()='Булки']");
    public static final By SAUCES_TAB = By.xpath("//span[text()='Соусы']");
    public static final By FILLINGS_TAB = By.xpath("//span[text()='Начинки']");

    // Локаторы для заголовков разделов, чтобы проверить переход
    public static final By BUNS_TITLE = By.xpath("//h2[text()='Булки']");
    public static final By SAUCES_TITLE = By.xpath("//h2[text()='Соусы']");
    public static final By FILLINGS_TITLE = By.xpath("//h2[text()='Начинки']");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Перейти в раздел 'Булки'")
    public void goToBuns() {
        wait.until(ExpectedConditions.elementToBeClickable(BUNS_TAB)).click();
    }

    @Step("Перейти в раздел 'Соусы'")
    public void goToSauces() {
        wait.until(ExpectedConditions.elementToBeClickable(SAUCES_TAB)).click();
    }

    @Step("Перейти в раздел 'Начинки'")
    public void goToFillings() {
        wait.until(ExpectedConditions.elementToBeClickable(FILLINGS_TAB)).click();
    }

    @Step("Проверить, что заголовок 'Булки' виден")
    public boolean isBunsTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(BUNS_TITLE)).isDisplayed();
    }

    @Step("Проверить, что заголовок 'Соусы' виден")
    public boolean isSaucesTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SAUCES_TITLE)).isDisplayed();
    }

    @Step("Проверить, что заголовок 'Начинки' виден")
    public boolean isFillingsTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(FILLINGS_TITLE)).isDisplayed();
    }
}