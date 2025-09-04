package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;

    // Локаторы
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By personalAccountButton = By.xpath("//a[text()='Личный кабинет']");
    private By constructorLink = By.xpath("//p[text()='Конструктор']");
    private By logo = By.className("AppHeader_header__logo__2D0X2");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать на кнопку 'Войти в аккаунт'")
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажать на кнопку 'Личный кабинет'")
    public void clickPersonalAccount() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Нажать на 'Конструктор'")
    public void clickConstructor() {
        driver.findElement(constructorLink).click();
    }

    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogo() {
        driver.findElement(logo).click();
    }
}
