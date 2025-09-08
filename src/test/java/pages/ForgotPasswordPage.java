package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final By EMAIL_FIELD = By.name("name");
    public static final By RESET_PASSWORD_BUTTON = By.xpath("//button[text()='Восстановить']");
    public static final By LOGIN_LINK = By.linkText("Войти");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Заполнить email для восстановления пароля")
    public void fillEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD)).sendKeys(email);
    }

    @Step("Нажать кнопку 'Восстановить'")
    public void clickResetPasswordButton() {
        wait.until(ExpectedConditions.elementToBeClickable(RESET_PASSWORD_BUTTON)).click();
    }

    @Step("Перейти на страницу логина")
    public LoginPage goToLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_LINK)).click();
        return new LoginPage(driver);
    }
}