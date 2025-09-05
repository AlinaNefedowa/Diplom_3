package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.UserGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NavigationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;
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
        step("Сгенерировать и зарегистрировать пользователя через API");
        user = UserGenerator.getRandomUser();
        Response response = UserApiClient.createUser(user);
        if (response.getStatusCode() == 200) {
            accessToken = response.then().extract().path("accessToken");
        }

        step("Войти в аккаунт через UI");
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage.clickPersonalAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        loginPage.clickLoginButton();

        step("Проверить, что вход успешен");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка перехода в личный кабинет")
    @Description("Переход в личный кабинет по клику на кнопку 'Личный кабинет'")
    public void goToPersonalAccountTest() {
        step("Нажать на кнопку 'Личный кабинет'");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Проверить, что страница профиля загрузилась");
        assertTrue(profilePage.isProfileTitleDisplayed());
    }

    @Test
    @DisplayName("Проверка перехода из личного кабинета в конструктор")
    @Description("Переход на страницу конструктора по клику на кнопку 'Конструктор' в личном кабинете")
    public void goToConstructorFromProfileTest() {
        step("Перейти в личный кабинет");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Нажать на ссылку 'Конструктор'");
        profilePage.goToConstructor();

        step("Проверить, что страница конструктора загрузилась");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка перехода из личного кабинета на главную по логотипу")
    @Description("Переход на главную страницу по клику на логотип Stellar Burgers")
    public void goToMainPageFromProfileByLogoTest() {
        step("Перейти в личный кабинет");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Нажать на логотип Stellar Burgers");
        profilePage.goToMainPageByLogo();

        step("Проверить, что страница конструктора загрузилась");
        assertTrue(mainPage.isCreateOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка выхода из аккаунта")
    @Description("Выход из аккаунта по кнопке 'Выйти' на странице личного кабинета")
    public void logoutTest() {
        step("Перейти в личный кабинет");
        mainPage.clickPersonalAccountButton();
        profilePage = new ProfilePage(driver);

        step("Нажать на кнопку 'Выйти'");
        profilePage.logout();

        step("Проверить, что кнопка 'Войти в аккаунт' отображается на главной странице");
        // Используем WebDriverWait для явного ожидания видимости элемента
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(MainPage.LOGIN_BUTTON_AFTER_LOGOUT));

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