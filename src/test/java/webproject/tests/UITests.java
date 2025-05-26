package webproject.tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import webproject.base.AuthorizedTest;
import webproject.elements.MainMenu;
import webproject.modaldialogs.AddNewWidgetDialog;
import webproject.pages.AllDashboardsPage;
import webproject.pages.DashboardPage;

import static org.assertj.core.api.Assertions.assertThat;

public class UITests extends AuthorizedTest {

    @Epic("Dashboard")
    @Feature("Widget Management")
    @Story("User adds new widget to an existing dashboard")
    @DisplayName("Add new widget to dashboard")
    @Description("Test verifies that a user can add a new widget to an existing dashboard and it appears correctly.")
    @Test
    public void addWidgetTest() {
        //Arrange
        String widgetTypeName = "Overall statistics";
        String filterName = "DEMO_FILTER";
        String widgetName = "TEST_WIDGET_1000";

        Allure.step("Open first dashboard from the list", MainMenu::allDashboardsButtonClick);

        String currentDashboardName = Allure.step("Open dashboard and capture its name", () -> {
            AllDashboardsPage allDashboardsPage = new AllDashboardsPage();
            allDashboardsPage.isPageLoaded();
            return allDashboardsPage.openFirstDashboard();
        });

        DashboardPage dashboardPage = new DashboardPage()
                .isPageLoaded()
                .checkDashboardName(currentDashboardName);

        //Act
        Allure.step("Start widget creation", () -> {
            dashboardPage.addNewWidget();
            AddNewWidgetDialog dialog = new AddNewWidgetDialog()
                    .isDialogPresent()
                    .selectWidgetByType(widgetTypeName)
                    .clickNextStepButton()
                    .selectFirstFilter()
                    .clickNextStepButton()
                    .setWidgetName(widgetName)
                    .clickAddButton();
            dashboardPage.isPageLoaded();
        });

        // Assert
        Allure.step("Verify widget is present and then delete it", () -> {
            SelenideElement addedWidget = dashboardPage.getWidgetByName(widgetName);
            assertThat(addedWidget)
                    .as("Widget with name '%s' should be present", widgetName)
                    .isNotNull();
            dashboardPage.deleteWidgetByName(widgetName);
            assertThat(dashboardPage.getWidgetByName(widgetName)).isNull();
        });
    }
}