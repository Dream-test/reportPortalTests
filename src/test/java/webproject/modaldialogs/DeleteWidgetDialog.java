package webproject.modaldialogs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.*;

public class DeleteWidgetDialog {
    private static final SelenideElement dialogForm = $("div.modalLayout__modal-window--jrhO6");
    private static final SelenideElement dialogTitle = $("span.modalHeader__modal-title--pusR8");

    public static void isDialogPresent() {
        dialogForm.shouldBe(visible);
        dialogForm.$("span.modalHeader__modal-title--pusR8")
                .shouldBe(visible)
                .shouldHave(Condition.text("Delete widget"));
    }

    public static void cancelButtonClick() {
        dialogForm.$(byText("Cancel")).shouldBe(visible).click();
        dialogForm.should(disappear);
    }

    public static void deleteButtonClick() {
        dialogForm.$(byText("Delete")).shouldBe(visible).click();
        dialogForm.should(disappear);
    }
}
