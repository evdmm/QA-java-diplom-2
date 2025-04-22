package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class UserSteps {

    @Step("Создание пользователя")
    public static Response createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(API_AUTH_REGISTER);
    }

    @Step("Логин пользователя")
    public static Response loginUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .post(API_AUTH_LOGIN);
    }

    @Step("Получение и установка валидного токена авторизации")
    public static void getAndSetValidAccessToken(User user) {
        String accessToken = loginUser(user).then().extract().body().path("accessToken").toString();
        user.setAccessToken(accessToken);
    }

    @Step("Получение и установка пустого токена авторизации")
    public static void getAndSetEmptyAccessToken(User user) {
        user.setAccessToken("");
    }

    @Step("Обновление пользователя с авторизацией")
    public static Response updateUserWithAuth(User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .and()
                .body(user)
                .when()
                .patch(API_AUTH_USER);
    }

    @Step("Обновление пользователя без авторизации")
    public static Response updateUserWithoutAuth(User user) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .patch(API_AUTH_USER);
    }

    @Step("Удаление пользователя")
    public static Response deleteUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .and()
                .body(user)
                .when()
                .delete(API_AUTH_USER);
    }
}
