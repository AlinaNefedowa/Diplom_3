package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти в аккаунт']");
    public static final By LOGIN_BUTTON_AFTER_LOGOUT = By.xpath("//button[text()='Войти']");
    public static final By PERSONAL_ACCOUNT_BUTTON = By.xpath("//a/p[text()='Личный Кабинет']");
    public static final By CONSTRUCTOR_LINK = By.xpath("//p[text()='Конструктор']");
    public static final By LOGO = By.className("AppHeader_header__logo__2D0X2");
    public static final By CREATE_ORDER_BUTTON = By.xpath("//button[text()='Оформить заказ']");

    public static final By BUNS_SECTION = By.xpath("//span[text()='Булки']");
    public static final By SAUCES_SECTION = By.xpath("//span[text()='Соусы']");
    public static final By FILLINGS_SECTION = By.xpath("//span[text()='Начинки']");

    public static final By BUNS_TITLE = By.xpath("//h2[text()='Булки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Нажать на кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    @Step("Нажать на кнопку 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(PERSONAL_ACCOUNT_BUTTON)).click();
    }

    @Step("Нажать на ссылку 'Конструктор'")
    public void clickConstructorLink() {
        wait.until(ExpectedConditions.elementToBeClickable(CONSTRUCTOR_LINK)).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGO)).click();
    }

    @Step("Проверить видимость кнопки 'Оформить заказ'")
    public boolean isCreateOrderButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CREATE_ORDER_BUTTON)).isDisplayed();
    }

    @Step("Перейти в раздел 'Булки'")
    public void clickBunsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(BUNS_SECTION)).click();
    }

    @Step("Перейти в раздел 'Соусы'")
    public void clickSaucesSection() {
        wait.until(ExpectedConditions.elementToBeClickable(SAUCES_SECTION)).click();
    }

    @Step("Перейти в раздел 'Начинки'")
    public void clickFillingsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(FILLINGS_SECTION)).click();
    }

    @Step("Проверить, что заголовок 'Булки' отображается")
    public boolean isBunsTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(BUNS_TITLE)).isDisplayed();
    }
}