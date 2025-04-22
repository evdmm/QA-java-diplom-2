import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.Order;
import ru.yandex.praktikum.User;

import static envconfig.EnvConfig.BASE_URI;
import static ru.yandex.praktikum.OrderChecks.*;
import static ru.yandex.praktikum.OrderSteps.*;
import static ru.yandex.praktikum.User.*;
import static ru.yandex.praktikum.UserSteps.*;

public class CreateOrderTest {

    public User user;
    public Order order;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        user = new User();
        user.setEmail(generateRandomEmail());
        user.setPassword(generateRandomPassword());
        user.setName(generateRandomName());
        createUser(user);
        order = new Order();
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void createOrderWithAuthAndIngredientsSuccess() {
        getAndSetValidAccessToken(user);
        addIngredientsListToOrder(order);
        Response response = createOrderWithAuth(order, user);
        createOrderSuccessChecks(response);
    }

    @Test
    @DisplayName("Ошибка создание заказа с авторизацией и без ингредиентов")
    public void createOrderWithAuthAndWithoutIngredientsError() {
        getAndSetValidAccessToken(user);
        addEmptyIngredientsListToOrder(order);
        Response response = createOrderWithAuth(order, user);
        createOrderWithoutIngredientsErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка создание заказа с авторизацией и невалидным хешем ингредиентов")
    public void createOrderWithAuthAndInvalidIngredientsHashError() {
        getAndSetValidAccessToken(user);
        addIngredientsListWithInvalidHashToOrder(order);
        Response response = createOrderWithAuth(order, user);
        createOrderWithInvalidHashIngredientsErrorChecks(response);
    }

    @Test
    @DisplayName("Ошибка создание заказа без авторизации")
    public void createOrderWithoutAuthError() {
        addIngredientsListToOrder(order);
        Response response = createOrderWithoutAuth(order);
        createOrderWithoutAuthErrorChecks(response);
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
