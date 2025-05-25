package webproject.base;

import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webproject.elements.MainMenu;
import webproject.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public abstract class AuthorizedTest extends UITestBase {
    Logger logger = LoggerFactory.getLogger(AuthorizedTest.class);
    private final String url = "https://demo.reportportal.io/ui/#login";
    private final String userName = "default";
    private final String password = "1q2w3e";

    @BeforeEach
    @Step("Login with name {name} and password {password}")
    public void login() {
        open(url);
        logger.info("Open url= {}", url);
        LoginPage.isPageLoaded();
        LoginPage.loginByNameAndPassword(userName, password );
        logger.info("UserName = {}, Password = {}", userName, password);
        MainMenu.isMenuExist();
    }
}
