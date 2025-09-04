package tests;

import api.UserApiClient;
import io.restassured.response.Response;
import models.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
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

        Response response = UserApiClient.createUser(user);
        accessToken = response.then().extract().path("accessToken");

        driver.get("https://qa-scooter.praktikum-services.ru/");
        loginPage = new LoginPage(driver);

        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();
    }

    @Test
    public void loginFromMainButton() {
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();

        // например проверка, что кнопка "Оформить заказ" стала видна
        assertTrue(driver.getPageSource().contains("Оформить заказ"));
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
