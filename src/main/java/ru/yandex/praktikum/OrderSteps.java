package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа с авторизацией")
    public static Response createOrderWithAuth(Order order, User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .and()
                .body(order)
                .when()
                .post(API_ORDERS);
    }

    @Step("Создание заказа без авторизации")
    public static Response createOrderWithoutAuth(Order order) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(API_ORDERS);
    }

    @Step("Добавление списка ингредиентов в заказ")
    public static void addIngredientsListToOrder(Order order) {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(INGREDIENT_ONE);
        ingredients.add(INGREDIENT_TWO);
        order.setIngredients(ingredients);
    }

    @Step("Добавление пустого списка ингредиентов в заказ")
    public static void addEmptyIngredientsListToOrder(Order order) {
        List<String> ingredients = new ArrayList<>();
        order.setIngredients(ingredients);
    }

    @Step("Добавление списка ингредиентов c неверным хешем в заказ")
    public static void addIngredientsListWithInvalidHashToOrder(Order order) {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(INGREDIENT_INVALID_HASH);
        order.setIngredients(ingredients);
    }

    @Step("Получение заказов с авторизацией")
    public static Response getOrdersWithAuth(User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .when()
                .get(API_ORDERS);
    }

    @Step("Получение заказов без авторизации")
    public static Response getOrdersWithoutAuth() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_ORDERS);
    }
}
