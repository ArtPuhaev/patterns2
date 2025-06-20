package ru.netology.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.tests.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.tests.DataGenerator.Registration.getUser;
import static ru.netology.tests.DataGenerator.getRandomLogin;
import static ru.netology.tests.DataGenerator.getRandomPassword;

import org.junit.jupiter.api.BeforeEach;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Валидная регистрация")
    void successfulRegistration() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button ").click();
        $$("h2").findBy(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Незарегистрированный пользователь")
    void incorrectUsername() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content").findBy(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Заблокированный пользователь")
    void theBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content").findBy(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Неверный пароль")
    void incorrectPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content").findBy(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Неверный логин")
    void incorrectLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content").findBy(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
