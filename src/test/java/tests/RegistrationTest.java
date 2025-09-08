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
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.BrowserManager;
import utils.UserGenerator;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private User user;
    private String accessToken;

    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        String browserName = testInfo.getDisplayName().toLowerCase().contains("yandex") ? "yandex" : "chrome";
        driver = BrowserManager.getDriver(browserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get("https://stellarburgers.nomoreparties.site/register");
        mainPage = new MainPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @AfterEach
    public void deleteUserAndTeardown() {
        if (accessToken != null) {
            UserApiClient.deleteUser(accessToken);
        }
        BrowserManager.quitDriver(driver);
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка успешной регистрации")
    @Description("Регистрация нового пользователя с валидными данными")
    public void successfulRegistrationTest(String browserName) {
        step("Сгенерировать данные нового пользователя");
        user = UserGenerator.getRandomUser();
        step("Заполнить форму регистрации и нажать 'Зарегистрироваться'");
        registrationPage.fillRegistrationForm(user.getName(), user.getEmail(), user.getPassword());
        registrationPage.clickRegisterButton();
        step("Проверить, что форма входа отображается после регистрации");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoginFormTitleDisplayed());
        Response loginResponse = UserApiClient.loginUser(user);
        if (loginResponse != null && loginResponse.getStatusCode() == 200) {
            accessToken = loginResponse.then().extract().path("accessToken");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"chrome", "yandex"})
    @DisplayName("Проверка регистрации с некорректным паролем")
    @Description("Попытка регистрации с паролем менее шести символов. Ожидается ошибка.")
    public void registrationWithInvalidPasswordTest(String browserName) {
        step("Сгенерировать пользователя с невалидным паролем");
        user = UserGenerator.getNewUserWithInvalidPassword();
        step("Заполнить форму регистрации и нажать 'Зарегистрироваться'");
        registrationPage.fillRegistrationForm(user.getName(), user.getEmail(), user.getPassword());
        registrationPage.clickRegisterButton();
        step("Проверить, что отображается сообщение об ошибке 'Некорректный пароль'");
        String expectedErrorText = "Некорректный пароль";
        String actualErrorText = registrationPage.getErrorText();
        assertEquals(expectedErrorText, actualErrorText);
    }
}