package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private User user;
    private String accessToken;

    @BeforeAll
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage(driver);
    }

    @BeforeEach
    public void createAndLoginUser() {
        user = UserGenerator.getRandomUser();
        Response response = UserApiClient.createUser(user);
        if (response.getStatusCode() == 200) {
            accessToken = response.then().extract().path("accessToken");
        }
        // Переход на главную страницу перед каждым тестом
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа по кнопке 'Войти в аккаунт' на главной странице")
    @Description("Успешный вход в аккаунт с главной страницы через кнопку 'Войти в аккаунт'")
    public void loginFromMainPageButtonTest() {
        step("Нажать на кнопку 'Войти в аккаунт'");
        mainPage.clickLoginButton();

        step("Заполнить форму входа и нажать кнопку 'Войти'");
        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();

        step("Проверить, что отображается кнопка 'Оформить заказ' на главной странице");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа через кнопку 'Личный кабинет'")
    @Description("Успешный вход в аккаунт через кнопку 'Личный кабинет' на главной странице")
    public void loginFromPersonalAccountButtonTest() {
        step("Нажать на кнопку 'Личный кабинет'");
        mainPage.clickPersonalAccountButton();

        step("Заполнить форму входа и нажать кнопку 'Войти'");
        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();

        step("Проверить, что отображается кнопка 'Оформить заказ' на главной странице");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка входа через ссылку на странице регистрации")
    @Description("Успешный вход в аккаунт через ссылку 'Войти' на странице регистрации")
    public void loginFromRegistrationPageLinkTest() {
        step("Нажать на кнопку 'Войти в аккаунт' на главной странице для перехода к форме входа");
        mainPage.clickLoginButton();

        step("Перейти на страницу регистрации");
        loginPage = new LoginPage(driver);
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
    public void loginFromForgotPasswordPageLinkTest() {
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

    @AfterEach
    public void deleteUser() {
        if (accessToken != null) {
            UserApiClient.deleteUser(accessToken);
        }
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}