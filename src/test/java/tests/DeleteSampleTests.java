package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static MainTests.Endpoints.USER_BY_ID;
import static MainTests.ResponseCodes.DELETED_STATUS_CODE;
import static io.restassured.RestAssured.given;

public class DeleteSampleTests extends BaseTest {

    @Test(priority = 5, description = "Delete user by ID")
    public static void deleteUserTest() {
        Response response = given().when().pathParam("id", 2).delete(USER_BY_ID).then().extract().response();
        Assert.assertEquals(response.statusCode(), DELETED_STATUS_CODE);
        System.out.println("Resource processed successfully with code " + DELETED_STATUS_CODE);
    }
}
