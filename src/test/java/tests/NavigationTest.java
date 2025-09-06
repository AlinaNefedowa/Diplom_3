package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;
import utils.BrowserManager;
import utils.UserGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NavigationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private User user;
    private String accessToken;

    @AfterAll
    public void tearDown() {
        BrowserManager.quitDriver(driver);
    }

    @BeforeEach
    public void createAndLoginUser() {
        step("Сгенерировать и зарегистрировать пользователя через API");
        user = UserGenerator.getRandomUser();
        Response response = UserApiClient.createUser(user);
        if (response.getStatusCode() == 200) {
            accessToken = response.then().extract().path("accessToken");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка перехода в личный кабинет")
    @Description("Переход в личный кабинет по клику на кнопку 'Личный кабинет'")
    public void goToPersonalAccountTest(String browserName) {
        setupTest(browserName);

        step("Нажать на кнопку 'Личный кабинет'");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Проверить, что страница профиля загрузилась");
        assertTrue(profilePage.isProfileTitleDisplayed());

        teardownTest();
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка перехода из личного кабинета в конструктор")
    @Description("Переход на страницу конструктора по клику на кнопку 'Конструктор' в личном кабинете")
    public void goToConstructorFromProfileTest(String browserName) {
        setupTest(browserName);

        step("Перейти в личный кабинет");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Нажать на ссылку 'Конструктор'");
        profilePage.goToConstructor();

        step("Проверить, что страница конструктора загрузилась");
        assertTrue(mainPage.isCreateOrderButtonVisible());

        teardownTest();
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка перехода из личного кабинета на главную по логотипу")
    @Description("Переход на главную страницу по клику на логотип Stellar Burgers")
    public void goToMainPageFromProfileByLogoTest(String browserName) {
        setupTest(browserName);

        step("Перейти в личный кабинет");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Нажать на логотип Stellar Burgers");
        profilePage.goToMainPageByLogo();

        step("Проверить, что страница конструктора загрузилась");
        assertTrue(mainPage.isCreateOrderButtonVisible());

        teardownTest();
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка выхода из аккаунта")
    @Description("Выход из аккаунта по кнопке 'Выйти' на странице личного кабинета")
    public void logoutTest(String browserName) {
        setupTest(browserName);

        step("Перейти в личный кабинет");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Нажать на кнопку 'Выйти'");
        profilePage.logout();

        step("Проверить, что кнопка 'Войти в аккаунт' отображается на главной странице");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(MainPage.LOGIN_BUTTON_AFTER_LOGOUT));

        teardownTest();
    }

    @AfterEach
    public void deleteUser() {
        if (accessToken != null) {
            UserApiClient.deleteUser(accessToken);
        }
    }

    private void setupTest(String browserName) {
        driver = BrowserManager.getDriver(browserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage(driver);

        step("Войти в аккаунт через UI");
        mainPage.clickPersonalAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();

        step("Проверить, что вход успешен");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    private void teardownTest() {
        BrowserManager.quitDriver(driver);
    }
}