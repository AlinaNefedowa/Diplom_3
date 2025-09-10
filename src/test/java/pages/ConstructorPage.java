package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final By BUNS_TAB = By.xpath("//span[text()='Булки']/..");
    public static final By SAUCES_TAB = By.xpath("//span[text()='Соусы']/..");
    public static final By FILLINGS_TAB = By.xpath("//span[text()='Начинки']/..");

    public static final By BUNS_TITLE = By.xpath("//h2[text()='Булки']");
    public static final By SAUCES_TITLE = By.xpath("//h2[text()='Соусы']");
    public static final By FILLINGS_TITLE = By.xpath("//h2[text()='Начинки']");

    public static final String ACTIVE_TAB_CLASS = "tab_tab_type_current__2BEPc";

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Перейти в раздел 'Булки'")
    public void goToBuns() {
        clickTab(BUNS_TAB);
    }

    @Step("Перейти в раздел 'Соусы'")
    public void goToSauces() {
        clickTab(SAUCES_TAB);
    }

    @Step("Перейти в раздел 'Начинки'")
    public void goToFillings() {
        clickTab(FILLINGS_TAB);
    }

    private void clickTab(By tabLocator) {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tabLocator));
        tab.click();
        wait.until(ExpectedConditions.attributeContains(tab, "class", ACTIVE_TAB_CLASS));
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

    @Step("Проверить, что таб 'Булки' активен")
    public boolean isBunsTabActive() {
        return driver.findElement(BUNS_TAB).getAttribute("class").contains(ACTIVE_TAB_CLASS);
    }

    @Step("Проверить, что таб 'Соусы' активен")
    public boolean isSaucesTabActive() {
        return driver.findElement(SAUCES_TAB).getAttribute("class").contains(ACTIVE_TAB_CLASS);
    }

    @Step("Проверить, что таб 'Начинки' активен")
    public boolean isFillingsTabActive() {
        return driver.findElement(FILLINGS_TAB).getAttribute("class").contains(ACTIVE_TAB_CLASS);
    }
}
