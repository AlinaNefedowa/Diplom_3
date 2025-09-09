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
    public static final By CREATE_ORDER_BUTTON = By.xpath("//button[text()='Оформить заказ']");

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

    @Step("Проверить видимость кнопки 'Оформить заказ'")
    public boolean isCreateOrderButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CREATE_ORDER_BUTTON)).isDisplayed();
    }



}