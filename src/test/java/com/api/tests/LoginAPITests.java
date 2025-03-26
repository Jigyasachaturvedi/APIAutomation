package com.api.tests;
import com.api.base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginAPITests extends BaseTest {

    String sheetName = "Login API TC";
    @BeforeClass
    public void setUp() throws IOException {
        testCaseMappings = super.readExcelSheet(sheetName);
    }
    @Test(description = "TC001", priority = 1)
    public void testValidEmailPassword() {
        Response response = executeRequest("TC001");
        System.out.println(response.toString());
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected success status for successful login");
    }
    @Test(description = "TC002", priority = 2)
    public void testInvalidEmailValidPassword() {
        Response response = executeRequest("TC002");
        System.out.println(response.toString());
        Assert.assertEquals(response.getStatusCode(), 400, "Expected error status for invalid email");
    }
    @Test(description = "TC003", priority = 3)
    public void testValidEmailInvalidPassword() {
        Response response = executeRequest("TC003");
        Assert.assertEquals(response.getStatusCode(), 400, "Expected error status for invalid password");
    }
    @Test(description = "TC004", priority = 4)
    public void testEmptyEmail() {
        Response response = executeRequest("TC004");
        //ResponseBody body = response.getBody();
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status for empty email");
    }
    @Test(description = "TC005", priority = 5)
    public void testEmptyPassword() {
        Response response = executeRequest("TC005");
        Assert.assertEquals(response.getStatusCode(), 400, "Expected error status for empty password");
    }
    @Test(description = "TC006", priority = 6)
    public void testCreateUserEmptyEmailAndPassword() {
        Response response = executeRequest("TC006");
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status for empty email and password");
    }

    @Test(description = "TC007", priority = 7)
    public void testEmailInRequestBody() {
        Response response = executeRequest("TC007");
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status no password key in request body");
    }

    @Test(description = "TC008", priority = 8)
    public void testPasswordInRequestBody() {
        Response response = executeRequest("TC008");
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status no mail key in request body");
    }

    @Test(description = "TC009", priority = 9)
    public void testDifferentCasing() {
        Response response = executeRequest("TC009");
        System.out.println("Response Body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400, "Expected error status for incorrect casing");
    }

    @Test(description = "TC010", priority = 10)
    public void testLogInWithoutStringDatatype() {
        Response response = executeRequest("TC010");
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status if string data type is not entered");
    }

    @Test(description = "TC011", priority = 11)
    public void tooMuchWhiteSpace() {
        Response response = executeRequest("TC011");
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status on too much white space");
    }

    @Test(description = "TC012", priority = 12)
    public void bothEmailAndPasswordSame() {
        Response response = executeRequest("TC012");
        Assert.assertEquals(response.getStatusCode(), 400, "Expected error status if same email and password");
    }

    @Test(description = "TC013", priority = 13)
    public void emptyRequestBody() {
        Response response = executeRequest("TC013");
        Assert.assertEquals(response.getStatusCode(), 422, "Expected error status when request body is empty");
    }
    @Test(description = "TC014", priority = 14)
    public void testResponseTime() {
        Response response = executeRequest("TC014");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected successful login within the acceptable limit");
    }
}
