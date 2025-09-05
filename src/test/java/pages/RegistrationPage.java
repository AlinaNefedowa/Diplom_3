package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для полей формы регистрации
    public static final By NAME_FIELD = By.xpath("//label[text()='Имя']/following-sibling::input");
    public static final By EMAIL_FIELD = By.xpath("//label[text()='Email']/following-sibling::input");
    public static final By PASSWORD_FIELD = By.xpath("//label[text()='Пароль*']/following-sibling::input");

    // Локаторы для кнопок и ссылок
    public static final By REGISTER_BUTTON = By.xpath("//button[text()='Зарегистрироваться']");
    public static final By LOGIN_LINK = By.linkText("Войти"); // Ссылка "Войти" на странице регистрации

    // Локатор для сообщения об ошибке
    public static final By ERROR_MESSAGE = By.className("input__error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Ввести данные пользователя: имя, email, пароль")
    public void fillRegistrationForm(String name, String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_FIELD)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD)).sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(REGISTER_BUTTON)).click();
    }

    @Step("Перейти на страницу логина")
    public LoginPage goToLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_LINK)).click();
        return new LoginPage(driver);
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)).getText();
    }

    @Step("Проверить, что поле email отображается")
    public boolean isEmailFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD)).isDisplayed();
    }
}