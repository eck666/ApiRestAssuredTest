package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static MainTests.Endpoints.USER_BY_ID;
import static MainTests.Formatter.convertResponseToJson;
import static MainTests.ResponseCodes.SUCCESS_STATUS_CODE;
import static io.restassured.RestAssured.given;
import static resources.Payload.createUpdateUserPayload;

public class PutSampleTests extends BaseTest {

    @Test(priority = 3, description = "Update user by ID PUT")
    public void updateUserPutTest() {
        int userID = 2;

        String user = "ECK";
        String job = "Test Automation";

        Response response = given()
                .pathParam("id", userID)
                .body(createUpdateUserPayload(user, job))
                .contentType(ContentType.JSON)
                .when().put(USER_BY_ID)
                .then().extract().response();
        JsonPath jsonPath = convertResponseToJson(response);
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(jsonPath.getString("name"), user);
        Assert.assertEquals(jsonPath.getString("job"), job);
        Assert.assertNotNull(jsonPath.getString("updatedAt"));
        System.out.println("Resource processed successfully with code " + SUCCESS_STATUS_CODE);
    }
}