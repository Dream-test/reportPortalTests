package webproject.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AllDashboardsPage {
    Logger logger = LoggerFactory.getLogger(AllDashboardsPage.class);
    private final SelenideElement pageTitle = $(By.cssSelector("span[title='All Dashboards']"));

    public void isPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(text("All Dashboards"));
    }

    public String openFirstDashboard() {
        ElementsCollection dashboards = $$(By.cssSelector("a.dashboardTable__name--t2a89"));
        logger.info("Dashboards on current page = {}", dashboards.size());
        dashboards.shouldBe(CollectionCondition.sizeGreaterThan(0));

        SelenideElement firstDashboard = dashboards.first();
        String firstDashboardName = firstDashboard.getText();
        logger.info("Opening first dashboard: {}", firstDashboardName);

        firstDashboard.click();
        pageTitle.shouldBe(disappear);

        return firstDashboardName;
    }
}
