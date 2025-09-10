package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.BrowserManager;
import utils.UserGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private User user;
    private String accessToken;

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        step("Сгенерировать и зарегистрировать пользователя через API");
        user = UserGenerator.getRandomUser();
        Response response = UserApiClient.createUser(user);
        if (response.getStatusCode() == 200) {
            accessToken = response.then().extract().path("accessToken");
        }
        String browserName = testInfo.getDisplayName().toLowerCase().contains("yandex") ? "yandex" : "chrome";
        driver = BrowserManager.getDriver(browserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void cleanup() {
        if (accessToken != null) {
            UserApiClient.deleteUser(accessToken);
        }
        if (driver != null) {
            BrowserManager.quitDriver(driver);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа по кнопке 'Войти в аккаунт' на главной странице")
    @Description("Успешный вход в аккаунт с главной страницы через кнопку 'Войти в аккаунт'")
    public void loginFromMainPageButtonTest(String browserName) {
        step("Нажать на кнопку 'Войти в аккаунт'");
        mainPage.clickLoginButton();
        step("Заполнить форму входа и нажать кнопку 'Войти'");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();
        step("Проверить, что отображается кнопка 'Оформить заказ' на главной странице");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа через кнопку 'Личный кабинет'")
    @Description("Успешный вход в аккаунт через кнопку 'Личный кабинет' на главной странице")
    public void loginFromPersonalAccountButtonTest(String browserName) {
        step("Нажать на кнопку 'Личный кабинет'");
        mainPage.clickPersonalAccountButton();
        step("Заполнить форму входа и нажать кнопку 'Войти'");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();
        step("Проверить, что отображается кнопка 'Оформить заказ' на главной странице");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа через ссылку на странице регистрации")
    @Description("Успешный вход в аккаунт через ссылку 'Войти' на странице регистрации")
    public void loginFromRegistrationPageLinkTest(String browserName) {
        step("Нажать на кнопку 'Войти в аккаунт' на главной странице для перехода к форме входа");
        mainPage.clickLoginButton();
        step("Перейти на страницу регистрации");
        LoginPage loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = loginPage.goToRegisterPage();
        step("Нажать на ссылку 'Войти' на странице регистрации");
        registrationPage.goToLoginPage();
        step("Заполнить форму входа и нажать кнопку 'Войти'");
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();
        step("Проверить, что отображается кнопка 'Оформить заказ' на главной странице");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа через ссылку на странице восстановления пароля")
    @Description("Успешный вход в аккаунт через ссылку 'Войти' на странице восстановления пароля")
    public void loginFromForgotPasswordPageLinkTest(String browserName) {
        step("Нажать на кнопку 'Войти в аккаунт' на главной странице");
        mainPage.clickLoginButton();
        step("Перейти на страницу восстановления пароля");
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordPage = loginPage.goToForgotPasswordPage();
        step("Нажать на ссылку 'Войти' на странице восстановления пароля");
        forgotPasswordPage.goToLoginPage();
        step("Заполнить форму входа и нажать кнопку 'Войти'");
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();
        step("Проверить, что отображается кнопка 'Оформить заказ' на главной странице");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }
}