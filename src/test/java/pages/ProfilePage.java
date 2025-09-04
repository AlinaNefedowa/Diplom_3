package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    private WebDriver driver;

    private By logoutButton = By.xpath("//button[text()='Выйти']");
    private By constructorLink = By.xpath("//p[text()='Конструктор']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Выйти из аккаунта")
    public void logout() {
        driver.findElement(logoutButton).click();
    }

    @Step("Перейти в Конструктор")
    public void goToConstructor() {
        driver.findElement(constructorLink).click();
    }
}

