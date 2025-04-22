package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderChecks {

    @Step("Проверка успешного создания заказа")
    public static void createOrderSuccessChecks(Response response) {
        response.then().assertThat()
                .body("name", notNullValue())
                .body("order.number", notNullValue())
                .body("success", equalTo(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Проверка ошибки создания заказа без авторизации")
    public static void createOrderWithoutAuthErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Проверка ошибки создания заказа без ингредиентов")
    public static void createOrderWithoutIngredientsErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Step("Проверка ошибки создания заказа с невалидным хешем ингредиентов")
    public static void createOrderWithInvalidHashIngredientsErrorChecks(Response response) {
        response.then().assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Проверка успешного получения заказа")
    public static void getOrderSuccessChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(true))
                .body("total", notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Проверка ошибки получения заказа")
    public static void getOrderErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
