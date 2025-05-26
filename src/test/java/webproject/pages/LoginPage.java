package webproject.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private static final SelenideElement pageTitle = $(By.cssSelector(".blockHeader__block-header--xXHaQ"));
    private static final SelenideElement loginInput = $(By.cssSelector(".inputOutside__input--Ad7Xu[name='login']"));
    private static final SelenideElement passwordInput = $(By.cssSelector(".inputOutside__input--Ad7Xu[name='password']"));
    private static final SelenideElement loginButton = $(By.cssSelector("button[type='submit']")).shouldHave(text("Login"));


    public static void isPageLoaded() {
        pageTitle.shouldBe(visible)
                .shouldHave(or("text check", text("login to your account"), text("Welcome, login to your account")));
    }

    public static void loginByNameAndPassword(String name, String password) {
        loginInput.shouldBe(visible).clear();
        loginInput.setValue(name);
        passwordInput.shouldBe(visible).clear();
        passwordInput.setValue(password);
        loginButton.shouldBe(visible).click();
        pageTitle.should(disappear);
    }
}
