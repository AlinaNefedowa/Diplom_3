package tests;

import api.UserApiClient;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static io.qameta.allure.Allure.step;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationTest {

    private WebDriver driver;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
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
    public void goToRegistrationPage() {
        step("Нажать на кнопку 'Личный кабинет'");
        mainPage.clickPersonalAccountButton();

        step("Перейти на страницу регистрации");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToRegisterPage();
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    @Description("Регистрация нового пользователя с валидными данными")
    public void successfulRegistrationTest() {
        step("Сгенерировать данные нового пользователя");
        user = UserGenerator.getRandomUser();

        step("Заполнить форму регистрации и нажать 'Зарегистрироваться'");
        registrationPage.fillRegistrationForm(user.getName(), user.getEmail(), user.getPassword());
        registrationPage.clickRegisterButton(); // Correct method call

        step("Проверить, что форма входа отображается после регистрации");
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoginFormTitleDisplayed());
    }

    @Test
    @DisplayName("Проверка регистрации с некорректным паролем")
    @Description("Попытка регистрации с паролем менее шести символов. Ожидается ошибка.")
    public void registrationWithInvalidPasswordTest() {
        step("Сгенерировать пользователя с невалидным паролем");
        user = UserGenerator.getNewUserWithInvalidPassword();

        step("Заполнить форму регистрации и нажать 'Зарегистрироваться'");
        registrationPage.fillRegistrationForm(user.getName(), user.getEmail(), user.getPassword());
        registrationPage.clickRegisterButton(); // Correct method call

        step("Проверить, что отображается сообщение об ошибке 'Некорректный пароль'");
        String expectedErrorText = "Некорректный пароль";
        String actualErrorText = registrationPage.getErrorText();
        assertEquals(expectedErrorText, actualErrorText);
    }

    @AfterEach
    public void deleteUser() {
        if (user != null && UserApiClient.loginUser(user) != null) {
            Response loginResponse = UserApiClient.loginUser(user);
            if (loginResponse.getStatusCode() == 200) {
                accessToken = loginResponse.then().extract().path("accessToken");
                UserApiClient.deleteUser(accessToken);
            }
        }
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}