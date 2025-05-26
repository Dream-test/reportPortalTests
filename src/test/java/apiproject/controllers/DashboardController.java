package apiproject.controllers;

import apiproject.configurations.TestConfig;
import apiproject.models.Dashboard;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class DashboardController {
    Logger logger = LoggerFactory.getLogger(Dashboard.class);

    private final RequestSpecification requestSpec;
    private final String project;

    public DashboardController() {
        RestAssured.defaultParser = Parser.JSON;

        this.project = TestConfig.getProject();

        this.requestSpec = given()
                .baseUri(TestConfig.getBaseUrl())
                .basePath("/" + project + "/")
                .header("Authorization", TestConfig.getAuthHeader())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filter(new AllureRestAssured());
    }

    @Step("Add new empty dashboard: {dashboard.name}")
    public Response addEmptyDashboard(String emptyDashboard) {
        Allure.step("Send POST request to create dashboard");
        logger.info("Sending request to add dashboard with body: {}", emptyDashboard);

        return given(requestSpec)
                .body(emptyDashboard)
                .log().all()
                .post("dashboard")
                .andReturn();
    }

    @Step("Get dashboard by ID: {id}")
    public Response getDashboardById(int id) {
        Allure.step("Send GET request for dashboard ID: " + id);
        logger.info("Getting dashboard with ID: {}", id);

        return given(requestSpec)
                .log().all()
                .get("dashboard/" + id)
                .andReturn();
    }

    @Step("Get all dashboards")
    public Response getAllDashboards() {
        Allure.step("Send GET request to get all dashboards");
        logger.info("Getting all dashboards");

        return given(requestSpec)
                .log().all()
                .get("dashboard")
                .andReturn();
    }

    @Step("Delete dashboard by ID: {id}")
    public Response deleteDashboardById(int id) {
        Allure.step("Send DELETE request for dashboard ID: " + id);
        logger.info("Deleting dashboard with ID: {}", id);

        return given(requestSpec)
                .log().all()
                .delete("dashboard/" + id)
                .andReturn();
    }
}
