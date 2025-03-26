package com.api.tests;
import com.api.base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;

public class FarmTaskTests extends BaseTest {
    String sheetName = "Farm Task TC";

    @BeforeClass
    public void setUp() throws IOException {
        testCaseMappings = super.readExcelSheet(sheetName);
    }

    @Test(description = "TC001", priority = 1)
    public void testValidData() {
        Response response = sendGetRequest("/api/v1/farms/tasks", authToken);
        response.then().statusCode(200);
        System.out.println("Farm Task Response Data: " + response.getBody().asString());
    }

    @Test(description = "TC002", priority = 2)
    public void testInvalidToken() {
        Response response = sendGetRequest("/api/v1/farms/tasks", refreshToken);
        response.then().statusCode(401);
        System.out.println("Farm Task Response Data: " + response.getBody().asString());
    }

    @Test(description = "TC003", priority = 3)
    public void testInvalidEndpoints() {
        Response response = sendGetRequest("/api/v1/farms/task", authToken);
        response.then().statusCode(404);
        System.out.println("Farm Task Response Data: " + response.getBody().asString());
    }
}


// get farm task endpoint is to get all the information using it about farm task so what else api test cases can be written for this? Think about it jigs.