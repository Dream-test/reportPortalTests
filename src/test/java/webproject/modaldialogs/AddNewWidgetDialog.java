package webproject.modaldialogs;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$$;
import static webproject.utils.CustomActions.waitForVisibilityAndClick;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AddNewWidgetDialog {
    private SelenideElement dialogContent;
    private ElementsCollection stepsList;
    private ElementsCollection buttons;
    private int step;

    public void isDialogPresent() {
        this.dialogContent = $(By.cssSelector("div.widgetWizardModal__modal-window--Kh7Ff"));
        dialogContent.shouldBe(visible);
        dialogContent.$(By.cssSelector("span.modalHeader__modal-title--pusR8"))
                .shouldBe(visible)
                .shouldHave(Condition.text("Add new widget"));
        this.step = 0;
        this.stepsList = dialogContent.$$("div.stepLabelItem__step-label-item--kSkLn");
        stepsList.get(step).shouldHave(Condition.cssClass("stepLabelItem__active--nOKyy"));
        this.buttons = dialogContent.$$(By.cssSelector("button.ghostButton__ghost-button--r7c9T"));
    }

    public void closeDialog() {
        waitForVisibilityAndClick(dialogContent.$(By.cssSelector("div.modalHeader__close-modal-icon--VeAZ5")));
        dialogContent.should(disappear);
    }

    public void selectWidgetByType(String widgetTypeName) {
        dialogContent.$(By.cssSelector("div.widgetTypeSelector__widget-type-selector-heading--a6h1k"))
                .shouldBe(visible)
                .shouldHave(Condition.text("Choose widget type from the list below"));
        ElementsCollection widgetTypes = $$(By.cssSelector("div.widgetTypeItem__widget-type-item--pkO3L"));
        SelenideElement needWidgetType = widgetTypes.findBy(Condition.text(widgetTypeName));
        needWidgetType.$(By.cssSelector("label")).click();
        needWidgetType.$(By.cssSelector("span.inputRadio__toggler--ygpdQ"))
                .shouldHave(Condition.cssClass("inputRadio__checked--BzIAN"));
        dialogContent.$(By.cssSelector("div.widgetInfoBlock__widget-title--bVFTW"))
                .shouldHave(Condition.text(widgetTypeName));
    }

    public void clickNextStepButton() {
        SelenideElement nextStepButton = buttons.findBy(Condition.text("Next step"));
        waitForVisibilityAndClick(nextStepButton);
        step++;
        stepsList.get(step).shouldHave(Condition.cssClass("stepLabelItem__active--nOKyy"));
    }

    public void clickPreviousStepButton() {
        SelenideElement previousStepButton = buttons.findBy(Condition.text("Previous step"));
        waitForVisibilityAndClick(previousStepButton);
        step--;
        stepsList.get(step).shouldHave(Condition.cssClass("stepLabelItem__active--nOKyy"));
    }

    public void selectFilterByName(String filterName) {
        dialogContent.$(By.cssSelector("span.activeFilter__filters-text--Y09hF"))
                .shouldBe(visible)
                .shouldHave(Condition.text("Choose filter from the list below"));
        ElementsCollection filters = $$(By.cssSelector("div.filtersItem__filter-item--DHlV9"));
        SelenideElement needFilter = filters.findBy(Condition.text(filterName));
        needFilter.$(By.cssSelector("label")).click();
        needFilter.$(By.cssSelector("span.inputRadio__toggler--ygpdQ"))
                .shouldHave(Condition.cssClass("inputRadio__checked--BzIAN"));
    }

    public void setWidgetName(String widgetName) {
        SelenideElement widgetNameInput = dialogContent.$(By.cssSelector("input.input__input--iYEmM"));
        widgetNameInput.clear();
        widgetNameInput.setValue(widgetName);

    }

    public void clickAddButton() {
        waitForVisibilityAndClick(dialogContent.$(By.cssSelector("button.bigButton__big-button--BmG4Q")));
        dialogContent.should(disappear);
    }




}
