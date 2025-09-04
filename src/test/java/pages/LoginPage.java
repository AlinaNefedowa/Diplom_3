package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[text()='Войти']");
    private By errorMessage = By.className("input__error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввести email и пароль")
    public void fillLoginForm(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать 'Войти'")
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    @Step("Получить текст ошибки входа")
    public String getErrorText() {
        return driver.findElement(errorMessage).getText();
    }
}
