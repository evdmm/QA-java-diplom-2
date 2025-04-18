import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.User;

import static envconfig.EnvConfig.BASE_URI;
import static ru.yandex.praktikum.OrderChecks.getOrderErrorChecks;
import static ru.yandex.praktikum.OrderChecks.getOrderSuccessChecks;
import static ru.yandex.praktikum.OrderSteps.*;
import static ru.yandex.praktikum.User.*;
import static ru.yandex.praktikum.UserSteps.*;
import static ru.yandex.praktikum.UserSteps.deleteUser;

public class GetOrderTest {

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
    @DisplayName("Получение заказов с авторизацией")
    public void getOrderWithAuthSuccess() {
        getAndSetValidAccessToken(user);
        Response response = getOrdersWithAuth(user);
        getOrderSuccessChecks(response);
    }

    @Test
    @DisplayName("Ошибка получение заказов без авторизации")
    public void getOrderWithoutAuthError() {
        Response response = getOrdersWithoutAuth();
        getOrderErrorChecks(response);
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
