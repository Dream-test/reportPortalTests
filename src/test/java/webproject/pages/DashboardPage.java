package webproject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webproject.modaldialogs.DeleteWidgetDialog;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static webproject.utils.CustomActions.waitForVisibilityAndClick;

public class DashboardPage {
    Logger logger = LoggerFactory.getLogger(DashboardPage.class);
    private final SelenideElement dashboardName = $$(By.cssSelector("li.pageBreadcrumbs__page-breadcrumbs-item--JhXKI")).get(1);
    private final ElementsCollection pageButtons = $$(By.cssSelector("span.ghostButton__text--SjHtK"));
    private ElementsCollection widgets;

    public DashboardPage isPageLoaded() {
        dashboardName.shouldBe(visible).shouldHave(Condition.attribute("title"));
        this.widgets = $$(By.cssSelector("div.react-grid-item"));
        return this;
    }

    public DashboardPage checkDashboardName(String name) {
        dashboardName.shouldHave(Condition.text(name));
        return this;
    }

    public void addNewWidget() {
        SelenideElement newWidgetButton = pageButtons.get(1);
        newWidgetButton.shouldBe(visible).shouldHave(Condition.text("Add new widget"));
        newWidgetButton.click();
    }

    public SelenideElement getWidgetByName(String widgetName) {
        return findWidgetByName(widgetName);
    }

    public void deleteWidgetByName(String widgetName) {
        SelenideElement deletingWidget = findWidgetByName(widgetName);
        if (deletingWidget != null) {
            SelenideElement widgetHeader = deletingWidget.$("div.widget__widget-header--YaPlQ");
            widgetHeader.hover();
            waitForVisibilityAndClick(widgetHeader.$$("svg").get(2));
            DeleteWidgetDialog.isDialogPresent();
            DeleteWidgetDialog.deleteButtonClick();

        }
    }

    private SelenideElement findWidgetByName(String widgetName) {
        for (SelenideElement widget : widgets) {
            if (widget.$("div").has(Condition.text(widgetName))) {
                return widget;
            }
        }
        logger.info("Can not find widget named: {}", widgetName);
        return null;
    }
}
