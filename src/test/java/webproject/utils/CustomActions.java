package webproject.utils;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;

public class CustomActions {
    public static void waitForVisibilityAndClick(SelenideElement element) {
        element.should(exist);
        element.shouldBe(visible).click();
    }

    public static void scrollToElement(SelenideElement element) {
        element.scrollIntoView(false).shouldBe(visible);
    }
}

