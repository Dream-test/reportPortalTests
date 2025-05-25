package webproject.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import webproject.elements.MainMenu;
import webproject.modaldialogs.AddNewWidgetDialog;
import webproject.pages.AllDashboardsPage;
import webproject.pages.DashboardPage;
import webproject.pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UITests {
    private final String url = "https://demo.reportportal.io/ui/#login";
    String userLogin = "standard_user";
    String password = "secret_sauce";

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void openLoginPage() {
        open(url);
    }

    @AfterEach
    public void cleanBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

    @AfterAll
    public static void tearDownAll() {
        closeWebDriver();
    }

    @Test
    public void addWidgetTest() {
        String name = "default";
        String password = "1q2w3e";
        String widgetTypeName = "Overall statistics";
        String filterName = "DEMO_FILTER";
        String widgetName = "TEST_WIDGET";

        LoginPage.isPageLoaded();
        LoginPage.loginByNameAndPassword(name, password);
        MainMenu.isMenuExist();
        MainMenu.allDashboardsButtonClick();
        AllDashboardsPage allDashboardsPage = new AllDashboardsPage();
        allDashboardsPage.isPageLoaded();
        String currentDashboardName = allDashboardsPage.openFirstDashboard();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.isPageLoaded();
        dashboardPage.checkDashboardName(currentDashboardName);
        dashboardPage.addNewWidget();
        AddNewWidgetDialog addNewWidgetDialog = new AddNewWidgetDialog();
        addNewWidgetDialog.isDialogPresent();
        addNewWidgetDialog.selectWidgetByType(widgetTypeName);
        addNewWidgetDialog.clickNextStepButton();
        addNewWidgetDialog.selectFilterByName(filterName);
        addNewWidgetDialog.clickNextStepButton();
        addNewWidgetDialog.setWidgetName(widgetName);
        addNewWidgetDialog.clickAddButton();
        dashboardPage.isPageLoaded();
        SelenideElement addedWidget = dashboardPage.getWidgetByName(widgetName);
        assertThat(addedWidget).isNotNull();
        dashboardPage.deleteWidgetByName(widgetName);

    }

}