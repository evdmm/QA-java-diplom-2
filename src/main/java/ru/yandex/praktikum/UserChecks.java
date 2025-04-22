package ru.yandex.praktikum;

import constants.Constants;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.*;

public class UserChecks {

    @Step("Проверка успешного создания пользователя")
    public static void createUserSuccessChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(true))
                .body("user.email", notNullValue())
                .body("user.name", notNullValue())
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Проверка ошибки создания пользователя без обязательного поля")
    public static void createUserErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    @Step("Проверка ошибки создания дубликата пользователя")
    public static void createDuplicateUserErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    @Step("Проверка успешного логина пользователя")
    public static void loginUserSuccessChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(true))
                .body("user.email", notNullValue())
                .body("user.name", notNullValue())
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Проверка ошибки логина пользователя без обязательного поля")
    public static void loginUserErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Проверка ошибки логина несуществующего пользователя")
    public static void loginNonExistentUserChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Проверка успешного изменения данных пользователя")
    public static void updateUserSuccessChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(true))
                .body("user.email", notNullValue())
                .body("user.name", notNullValue())
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    @Step("Проверка ошибки изменения данных пользователя")
    public static void updateUserErrorChecks(Response response) {
        response.then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
