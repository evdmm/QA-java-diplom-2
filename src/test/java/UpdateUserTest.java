import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.User;

import static envconfig.EnvConfig.BASE_URI;
import static ru.yandex.praktikum.User.*;
import static ru.yandex.praktikum.UserChecks.updateUserErrorChecks;
import static ru.yandex.praktikum.UserChecks.updateUserSuccessChecks;
import static ru.yandex.praktikum.UserSteps.*;

public class UpdateUserTest {

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
    @DisplayName("Изменение email пользователя")
    public void updateUserEmailSuccess() {
        getAndSetValidAccessToken(user);
        user.setEmail(generateRandomEmail());
        Response response = updateUserWithAuth(user);
        updateUserSuccessChecks(response);
    }

    @Test
    @DisplayName("Изменение пароля пользователя")
    public void updateUserPasswordSuccess() {
        getAndSetValidAccessToken(user);
        user.setPassword(generateRandomPassword());
        Response response = updateUserWithAuth(user);
        updateUserSuccessChecks(response);
    }

    @Test
    @DisplayName("Изменение имени пользователя")
    public void updateUserNameSuccess() {
        getAndSetValidAccessToken(user);
        user.setName(generateRandomName());
        Response response = updateUserWithAuth(user);
        updateUserSuccessChecks(response);
    }

    @Test
    @DisplayName("Ошибка изменения email пользователя без авторизации")
    public void updateUserEmailError() {
        getAndSetEmptyAccessToken(user);
        user.setEmail(generateRandomEmail());
        Response response = updateUserWithoutAuth(user);
        updateUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка изменения пароля пользователя без авторизации")
    public void updateUserPasswordError() {
        getAndSetEmptyAccessToken(user);
        user.setEmail(generateRandomPassword());
        Response response = updateUserWithoutAuth(user);
        updateUserErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка изменения имени пользователя без авторизации")
    public void updateUserNameError() {
        getAndSetEmptyAccessToken(user);
        user.setEmail(generateRandomName());
        Response response = updateUserWithoutAuth(user);
        updateUserErrorChecks(response);
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
