package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final By EMAIL_FIELD = By.name("name");
    public static final By PASSWORD_FIELD = By.name("Пароль");

    public static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти']");
    public static final By FORGOT_PASSWORD_LINK = By.linkText("Восстановить пароль");
    public static final By REGISTER_LINK = By.linkText("Зарегистрироваться");

    public static final By LOGIN_FORM_TITLE = By.xpath("//h2[text()='Вход']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ввести email и пароль")
    public void fillLoginForm(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD)).sendKeys(email);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(PASSWORD_FIELD));
        wait.until(ExpectedConditions.elementToBeClickable(PASSWORD_FIELD)).sendKeys(password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }


    @Step("Перейти на страницу восстановления пароля")
    public ForgotPasswordPage goToForgotPasswordPage() {
        wait.until(ExpectedConditions.elementToBeClickable(FORGOT_PASSWORD_LINK)).click();
        return new ForgotPasswordPage(driver);
    }

    @Step("Перейти на страницу регистрации")
    public RegistrationPage goToRegisterPage() {
        wait.until(ExpectedConditions.elementToBeClickable(REGISTER_LINK)).click();
        return new RegistrationPage(driver);
    }

    @Step("Проверить, что форма входа отображается")
    public boolean isLoginFormTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_FORM_TITLE)).isDisplayed();
    }
}