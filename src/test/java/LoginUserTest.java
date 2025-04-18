import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.User;

import static envconfig.EnvConfig.BASE_URI;
import static ru.yandex.praktikum.User.*;
import static ru.yandex.praktikum.UserChecks.*;
import static ru.yandex.praktikum.UserSteps.*;

public class LoginUserTest {

    public User user;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        user = new User();
        user.setEmail(generateRandomEmail());
        user.setPassword(generateRandomPassword());
        user.setName(generateRandomName());
        createUser(user);
    }

    @Test
    @DisplayName("Логин пользователя")
    public void loginUserSuccess() {
        Response response = loginUser(user);
        loginUserSuccessChecks(response);
    }

    @Test
    @DisplayName("Ошибка логина пользователя без email")
    public void loginUserWithoutEmail() {
        user.setEmail("");
        Response response = loginUser(user);
        loginUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка логина пользователя без пароля")
    public void loginUserWithoutPassword() {
        user.setPassword("");
        Response response = loginUser(user);
        loginUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка логина пользователя без существующего email")
    public void loginWithNonExistentUserEmail() {
        user.setEmail(generateRandomEmail());
        Response response = loginUser(user);
        loginNonExistentUserChecks(response);
    }

    @Test
    @DisplayName("Ошибка логина пользователя без существующего пароля")
    public void loginWithNonExistentUserPassword() {
        user.setEmail(generateRandomPassword());
        Response response = loginUser(user);
        loginNonExistentUserChecks(response);
    }

    @After
    public void tearDown() {
        String accessToken = loginUser(user).then().extract().body().path("accessToken");
        if (accessToken != null) {
            user.setAccessToken(accessToken);
            deleteUser(user);
        }
    }
}
