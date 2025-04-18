import io.qameta.allure.Description;
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

public class CreateUserTest {

    public User user;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        user = new User();
        user.setEmail(generateRandomEmail());
        user.setPassword(generateRandomPassword());
        user.setName(generateRandomName());
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUserSuccess() {
        Response response = createUser(user);
        createUserSuccessChecks(response);
    }

    @Test
    @DisplayName("Ошибка создания пользователя без email")
    public void createUserWithoutEmail() {
        user.setEmail("");
        Response response = createUser(user);
        createUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка создания пользователя без пароля")
    public void createUserWithoutPassword() {
        user.setPassword("");
        Response response = createUser(user);
        createUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка создания пользователя без имени")
    public void createUserWithoutName() {
        user.setName("");
        Response response = createUser(user);
        createUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка создания дубликата пользователя")
    public void createDuplicateUser() {
        createUser(user);
        Response response = createUser(user);
        createDuplicateUserErrorChecks(response);
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
