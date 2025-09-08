package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final By LOGOUT_BUTTON = By.xpath("//button[text()='Выход']");
    public static final By CONSTRUCTOR_LINK = By.xpath("//p[text()='Конструктор']");

    public static final By PROFILE_TITLE = By.xpath("//a[text()='Профиль']");

    public static final By LOGO = By.className("AppHeader_header__logo__2D0X2");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Нажать кнопку 'Выйти' из аккаунта")
    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON)).click();
    }

    @Step("Нажать на 'Конструктор' для перехода")
    public void goToConstructor() {
        wait.until(ExpectedConditions.elementToBeClickable(CONSTRUCTOR_LINK)).click();
    }

    @Step("Нажать на логотип для перехода на главную страницу")
    public void goToMainPageByLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGO)).click();
    }

    @Step("Проверить, что страница профиля загрузилась")
    public boolean isProfileTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PROFILE_TITLE)).isDisplayed();
    }
}