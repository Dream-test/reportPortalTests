package webproject.elements;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainMenu {
    static Logger logger = LoggerFactory.getLogger(MainMenu.class);
    private final static ElementsCollection mainMenuButtons = $$(By.cssSelector("a.sidebarButton__nav-link--gZnHQ"));

    public static void isMenuExist() {
        mainMenuButtons.shouldHave(CollectionCondition.size(9));
        logger.info("Main menu size = {}", mainMenuButtons.size());
    }

    public static void allDashboardsButtonClick() {
       SelenideElement allDashboardsButton = mainMenuButtons.findBy(Condition.attributeMatching("href", ".*dashboard.*"));
       logger.info("href= {}", allDashboardsButton.getAttribute("href"));
       allDashboardsButton.click();

    }

}
