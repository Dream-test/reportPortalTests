package apiproject.tests;

import apiproject.controllers.DashboardController;
import apiproject.models.Dashboard;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webproject.utils.DashboardUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dashboard creation tests")
public class DashboardApiTests {
   private static final Logger logger = LoggerFactory.getLogger(DashboardApiTests.class);
    DashboardController controller = new DashboardController();

    @BeforeEach
    void logTestStart(TestInfo testInfo) {
        logger.info(">>> Starting test: {}", testInfo.getDisplayName());
    }

    @AfterEach
    void logTestEnd(TestInfo testInfo) {
        logger.info("<<< Finished test: {}", testInfo.getDisplayName());
    }

    @Nested
    class PositiveCases {

        @Epic("Dashboards API")
        @Feature("Dashboard creation")
        @Severity(SeverityLevel.CRITICAL)
        @Story("User adds new empty dashboard")
        @Tag("smoke")
        @DisplayName("Check status 201, name and dashboard exist when add empty dashboard")
        @ParameterizedTest(name = "[{index}] {2}")
        @CsvFileSource(resources = "/testdata/dashboards_valid.csv", numLinesToSkip = 1)
        void checkCreateDashboardWithValidInput(String description, String name, String testType) {
            //Arrange
            logger.info("Test Type: {}", testType);
            logger.info("Dashboard that will be created name: {}", name);
            logger.info("Dashboard that will be created description: {}", description);

            //Act
            Response createResponse = controller.addEmptyDashboard(DashboardUtils.buildDashboardJson(description, name));
            logResponse(createResponse, "Create Response");
            ;

            //Assert
            assertThat(createResponse.statusCode()).isEqualTo(201);
            try {
                int newEmptyDashboardId = Integer.parseInt(createResponse.jsonPath().getString("id"));
                logger.info("Created dashboard id= {}", newEmptyDashboardId);

                Response getResponse = controller.getDashboardById(newEmptyDashboardId);
                logResponse(getResponse, "Get Response");
                assertThat(getResponse.statusCode()).isEqualTo(200);
                Dashboard createdDashboard = getResponse.as(Dashboard.class);
                logger.info("Existing dashboard name: {}", createdDashboard.getName());
                assertThat(createdDashboard.getName()).isEqualTo(name);
                logger.info("Existing dashboard description: {}", createdDashboard.getDescription());
                assertThat(createdDashboard.getDescription()).isEqualTo(description);

                Response deleteResponse = controller.deleteDashboardById(newEmptyDashboardId);
                logResponse(deleteResponse, "Delete Response");
                String expectedMessage = String.format("Dashboard with ID = '%d' successfully deleted.", newEmptyDashboardId);
                assertThat(deleteResponse.statusCode()).isEqualTo(200);
                assertThat(deleteResponse.jsonPath().getString("message"))
                        .isEqualTo(expectedMessage);
            } catch (NumberFormatException e) {
                logger.info("Create Response dashboard 'id' is not correct");
            }
        }
    }


    @Nested
    class NegativeCases {

        @Epic("Dashboards API")
        @Feature("Dashboard creation")
        @Severity(SeverityLevel.CRITICAL)
        @Story("User adds new empty dashboard")
        @Tag("smoke")
        @DisplayName("Check status 400 and not exist when try add dashboard with invalid params")
        @ParameterizedTest(name = "[{index}] {3}")
        @CsvFileSource(resources = "/testdata/dashboards_invalid.csv", numLinesToSkip = 1)
        void checkFailToCreateDashboardWithInvalidInput(String description, String name, String message, String testType) {
            //Arrange
            logger.info("Test Type: {}", testType);
            logger.info("Dashboard that will be created name: {}", name);
            logger.info("Dashboard that will be created description: {}", description);
            logger.info("Expected error message: {}", message);

            //Act
            Response createResponse = controller.addEmptyDashboard(DashboardUtils.buildDashboardJson(description,name));
            logResponse(createResponse, "Invalid Create Response");

            //Assert
            assertThat(createResponse.statusCode()).isEqualTo(400);
            assertThat(createResponse.jsonPath().getString("message"))
                    .isEqualTo(message);

            Response getAllResponse = controller.getAllDashboards();
            logger.info("Invalid getAllResponse Status: {}", getAllResponse.statusCode());
            assertThat(getAllResponse.statusCode()).isEqualTo(200);

            List<String> dashboardDescriptions = getAllResponse.jsonPath().getList("content.description");
            logger.info("Existing dashboards descriptions list: {}", dashboardDescriptions);
            assertThat(dashboardDescriptions).doesNotContain(description);
        }

        @Epic("Dashboards API")
        @Feature("Dashboard creation")
        @Severity(SeverityLevel.CRITICAL)
        @Story("User adds new empty dashboard")
        @Test
        @Tag("smoke")
        @DisplayName("Check status 409 and not exist when try add dashboard with exist name")
        void checkFailToCreateDashboardWithExistName() {
            //Arrange
            String name = "Test_Dashboard_with_next";
            String description = "First added dashboard";
            String nextDescription = "Second added dashboard";
            String message = "Resource 'Test_Dashboard_with_next' already exists. You couldn't create the duplicate.";

            logger.info("Dashboard that will be created name: {}", name);
            logger.info("Dashboard that will be created description: {}", description);
            logger.info("Second dashboard that will be created description: {}", nextDescription);

            //Act
            Response createResponse = controller.addEmptyDashboard(DashboardUtils.buildDashboardJson(description, name));
            logResponse(createResponse, "Create Response");

            assertThat(createResponse.statusCode()).isEqualTo(201);
            try {
                int newEmptyDashboardId = Integer.parseInt(createResponse.jsonPath().getString("id"));
                logger.info("Created dashboard id= {}", newEmptyDashboardId);

                Response getResponse = controller.getDashboardById(newEmptyDashboardId);
                logResponse(getResponse, "Get Response");
                assertThat(getResponse.statusCode()).isEqualTo(200);
                Dashboard createdDashboard = getResponse.as(Dashboard.class);
                logger.info("Existing dashboard name: {}", createdDashboard.getName());
                assertThat(createdDashboard.getName()).isEqualTo(name);
                logger.info("Existing dashboard description: {}", createdDashboard.getDescription());
                assertThat(createdDashboard.getDescription()).isEqualTo(description);

                Response secondCreateResponse = controller.addEmptyDashboard(DashboardUtils.buildDashboardJson(nextDescription, name));
                logResponse(secondCreateResponse, "Second Create Response");

                //Assert
                assertThat(secondCreateResponse.statusCode()).isEqualTo(409);
                assertThat(secondCreateResponse.jsonPath().getString("message"))
                        .isEqualTo(message);

                Response getAllResponse = controller.getAllDashboards();
                logger.info("Invalid getAllResponse Status: {}", getAllResponse.statusCode());
                assertThat(getAllResponse.statusCode()).isEqualTo(200);

                List<String> dashboardDescriptions = getAllResponse.jsonPath().getList("content.description");
                logger.info("Existing dashboards descriptions list: {}", dashboardDescriptions);
                assertThat(dashboardDescriptions).doesNotContain(nextDescription);

                Response deleteResponse = controller.deleteDashboardById(newEmptyDashboardId);
                logResponse(deleteResponse, "Delete Response");
                String expectedMessage = String.format("Dashboard with ID = '%d' successfully deleted.", newEmptyDashboardId);
                assertThat(deleteResponse.statusCode()).isEqualTo(200);
                assertThat(deleteResponse.jsonPath().getString("message"))
                        .isEqualTo(expectedMessage);
            } catch (NumberFormatException e) {
                logger.info("Create Response dashboard 'id' is not correct");
            }
        }
    }

    private void logResponse(Response response, String label) {
        logger.info("{} Status: {}", label, response.statusCode());
        logger.info("{} Body: {}", label, response.getBody().prettyPrint());
    }
}