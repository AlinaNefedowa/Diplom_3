package tests;

import api.UserApiClient;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    private String email;
    private String password;
    private String name;
    private String accessToken;

    @BeforeAll
    public void setUp() {
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void createUser() {
        User user = UserGenerator.getRandomUser();
        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();

        Response response = UserApiClient.createUser(user);
        accessToken = response.then().extract().path("accessToken");

        driver.get("https://stellarburgers.nomoreparties.site/");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginFromMainButton() {
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        assertTrue(loginPage.isCreateOrderButtonDisplayed());
    }

    @Test
    public void loginFromPersonalAccountButton() {
        RegistrationPage registrationPage = loginPage.goToRegisterPage();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.clickRegister();

        loginPage = registrationPage.goToLoginPage();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();

        assertTrue(loginPage.isCreateOrderButtonDisplayed());
    }

    @Test
    public void loginFromRegistrationForm() {
        RegistrationPage registrationPage = loginPage.goToRegisterPage();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.clickRegister();

        loginPage = registrationPage.goToLoginPage();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();

        assertTrue(loginPage.isCreateOrderButtonDisplayed());
    }

    @Test
    public void loginFromForgotPasswordForm() {
        ForgotPasswordPage forgotPasswordPage = loginPage.goToForgotPasswordPage();
        forgotPasswordPage.fillEmail(email);
        forgotPasswordPage.clickResetPassword();

        loginPage = forgotPasswordPage.goToLoginPage();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();

        assertTrue(loginPage.isCreateOrderButtonDisplayed());
    }

    @AfterEach
    public void deleteUser() {
        UserApiClient.deleteUser(accessToken);
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}
