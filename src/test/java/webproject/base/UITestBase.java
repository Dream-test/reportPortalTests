package webproject.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class UITestBase {

    @BeforeAll
    public static void configurationBrowser() {
        Configuration.browserSize = "1366x768";
        Configuration.headless = false;
        Configuration.timeout = 10000;
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
}
