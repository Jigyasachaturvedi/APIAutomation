package com.api.tests;

import com.api.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ForgotPasswordTests extends BaseTest {

    String sheetName = "Forgot Password TC";

    @BeforeClass
    public void setUp() throws IOException {
        testCaseMappings = super.readExcelSheet(sheetName);
    }

    @Test(description = "TC001", priority = 1)
    public void testValidEmail() {
        Response response = executeRequest("TC001");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "TC002", priority = 2)
    public void testInvalidEmail() {
        Response response = executeRequest("TC002");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }

    @Test(description = "TC003", priority = 3)
    public void testInvalidRequestBody() {
        Response response = executeRequest("TC003");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 500);
    }

    @Test(description = "TC004", priority = 4)
    public void testEmptyRequestBody() {
        Response response = executeRequest("TC004");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }

    @Test(description = "TC005", priority = 5)
    public void testUsernameFieldInRequest() {
        Response response = executeRequest("TC005");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }

    @Test(description = "TC006", priority = 6)
    public void testPasswordFieldInRequest() {
        Response response = executeRequest("TC006");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }

    @Test(description = "TC007", priority = 7)
    public void testNullEmail() {
        Response response = executeRequest("TC007");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }

    @Test(description = "TC008", priority = 8)
    public void testEmptyStringMail() {
        Response response = executeRequest("TC008");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }

    @Test(description = "TC009", priority = 9)
    public void testFieldUserEmail() {
        Response response = executeRequest("TC009");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test(description = "TC010", priority = 10)
    public void testPlannerEmail() {
        Response response = executeRequest("TC010");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "TC011", priority = 11)
    public void testNonExistingEMail() {
        Response response = executeRequest("TC011");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test(description = "TC012", priority = 12)
    public void testUpperCasedEmail() {
        Response response = executeRequest("TC012");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test(description = "TC013", priority = 13)
    public void testEmailWithSpace() {
        Response response = executeRequest("TC013");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 422);
    }
}