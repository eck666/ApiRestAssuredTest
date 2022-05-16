package tests;


import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static MainTests.Endpoints.USERS;
import static MainTests.Endpoints.USER_BY_ID;
import static MainTests.Formatter.convertResponseToJson;
import static MainTests.ResponseCodes.*;
import static io.restassured.RestAssured.given;

public class GetSampleTests extends BaseTest {

    @Test(priority = 0, description = "Get user by ID")
    public void getUsersByIDTest() {
        int userID = 2;
        Response response = given().filter(new RequestLoggingFilter())
                .when().pathParam("id", userID).get(USER_BY_ID)
                .then().extract().response();

        JsonPath jsonPath = convertResponseToJson(response);
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(jsonPath.getInt("data.id"), userID);
        Assert.assertEquals(jsonPath.getString("data.first_name"), "Janet");
        Assert.assertEquals(jsonPath.getString("data.last_name"), "Weaver");
        Assert.assertEquals(jsonPath.getString("data.email"), "janet.weaver@reqres.in");
        System.out.println("Resource processed successfully with code " + SUCCESS_STATUS_CODE);
    }

    @Test(priority = 1, description = "Get User List By Page Number")
    public void getUserListByPageTest() {
        int pageNumber = 2;
        Response response = given().filter(new RequestLoggingFilter())
                .queryParam("page", pageNumber).when().get(USERS)
                .then().extract().response();

        JsonPath jsonPath = convertResponseToJson(response);
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(jsonPath.getInt("page"), pageNumber);
        Assert.assertEquals(jsonPath.getInt("per_page"), 6);
        Assert.assertEquals(jsonPath.getInt("data[0].id"), 7);
        Assert.assertEquals(jsonPath.getString("data[0].email"), "michael.lawson@reqres.in");
        Assert.assertEquals(jsonPath.getString("data[0].first_name"), "Michael");
        Assert.assertEquals(jsonPath.getString("data[0].last_name"), "Lawson");
        System.out.println("Resource processed successfully with code " + SUCCESS_STATUS_CODE);
    }

    @Test(priority = 6, description = "User not found user ID")
    public void userNotFoundTest() {
        int userID = 23;
        Response response = given().filter(new RequestLoggingFilter())
                .when().pathParam("id", userID).get(USER_BY_ID)
                .then().extract().response();
        Assert.assertEquals(response.statusCode(), NOT_FOUND_STATUS_CODE);
    }
}