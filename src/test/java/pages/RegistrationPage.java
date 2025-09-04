package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private WebDriver driver;

    private By nameField = By.name("name");
    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By errorMessage = By.className("input__error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнить форму регистрации")
    public void fillRegistrationForm(String name, String email, String password) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать 'Зарегистрироваться'")
    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    @Step("Получить текст ошибки")
    public String getErrorText() {
        return driver.findElement(errorMessage).getText();
    }
}
