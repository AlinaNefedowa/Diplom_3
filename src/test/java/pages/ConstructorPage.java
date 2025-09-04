package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConstructorPage {
    private WebDriver driver;

    private By bunsTab = By.xpath("//span[text()='Булки']");
    private By saucesTab = By.xpath("//span[text()='Соусы']");
    private By fillingsTab = By.xpath("//span[text()='Начинки']");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Перейти в раздел 'Булки'")
    public void goToBuns() {
        driver.findElement(bunsTab).click();
    }

    @Step("Перейти в раздел 'Соусы'")
    public void goToSauces() {
        driver.findElement(saucesTab).click();
    }

    @Step("Перейти в раздел 'Начинки'")
    public void goToFillings() {
        driver.findElement(fillingsTab).click();
    }
}
