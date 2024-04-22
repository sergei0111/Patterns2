package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static DataGenerator.DataGenerator.Registration.getRegisteredUser;
import static DataGenerator.DataGenerator.Registration.getUser;
import static DataGenerator.DataGenerator.getRandomLogin;
import static DataGenerator.DataGenerator.getRandomPassword;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class Tests {

    String loginSelector = "[data-test-id='login'] input";
    String passwordSelector = "[data-test-id='password'] input";
    String buttonSelector = ".button";
    String headSelector = ".heading";
    String successMsg = "Личный кабинет";
    String errorMsgSelector = ".notification .notification__content";
    String errorMsg = "Ошибка! " + "Неверно указан логин или пароль";

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $(loginSelector).setValue(registeredUser.getLogin());
        $(passwordSelector).setValue(registeredUser.getPassword());
        $(buttonSelector).click();
        $(headSelector).shouldBe(visible).shouldHave(text(successMsg));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $(loginSelector).setValue(notRegisteredUser.getLogin());
        $(passwordSelector).setValue(notRegisteredUser.getPassword());
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text(errorMsg));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $(loginSelector).setValue(blockedUser.getLogin());
        $(passwordSelector).setValue(blockedUser.getPassword());
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $(loginSelector).setValue(wrongLogin);
        $(passwordSelector).setValue(registeredUser.getPassword());
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text(errorMsg));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $(loginSelector).setValue(registeredUser.getLogin());
        $(passwordSelector).setValue(wrongPassword);
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text(errorMsg));
    }

    @Test
    @DisplayName("Should get error message if login with not registered and blocked user")
    void shouldGetErrorIfNotRegisteredBlockedUser() {
        var notRegisteredUser = getUser("blocked");
        $(loginSelector).setValue(notRegisteredUser.getLogin());
        $(passwordSelector).setValue(notRegisteredUser.getPassword());
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text(errorMsg));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password and user blocked")
    void shouldGetErrorIfWrongPasswordAndUserBlocked() {
        var registeredUser = getRegisteredUser("blocked");
        var wrongPassword = getRandomPassword();
        $(loginSelector).setValue(registeredUser.getLogin());
        $(passwordSelector).setValue(wrongPassword);
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text(errorMsg));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login and blocked user")
    void shouldGetErrorIfWrongLoginAndBlockedUser() {
        var registeredUser = getRegisteredUser("blocked");
        var wrongLogin = getRandomLogin();
        $(loginSelector).setValue(wrongLogin);
        $(passwordSelector).setValue(registeredUser.getPassword());
        $(buttonSelector).click();
        $(errorMsgSelector).shouldBe(visible).shouldHave(text(errorMsg));
    }
}