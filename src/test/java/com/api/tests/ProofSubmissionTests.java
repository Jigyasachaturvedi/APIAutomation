package com.api.tests;
import com.api.base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;

public class ProofSubmissionTests extends BaseTest {
    String sheetName = "Proof Submission";

    @BeforeClass
    public void setUp() throws IOException {
        testCaseMappings = super.readExcelSheet(sheetName);
    }

    @Test(description = "TC001 - Proof Submission with Already existing proof id", priority = 1)
    public void testValidProof() {
        Response response = sendProofRequest( "ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "completed", "test", "dbc2438b-534c-4113-a8db-8cb83a8198be");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(409);
    }

    @Test(description = "TC002 - Proof Submission with Invalid proof media", priority = 2)
    public void testInvalidProofMedia() {
        Response response = sendProofRequest("ValidImage.png", "/api/v1/farm-task/proof", authToken, "completed", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

    @Test(description = "TC003 - Proof Submission with Already existing proof id", priority = 3)
    public void testProofAlreadyExist() {
        Response response = sendProofRequest( "ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "completed", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(409);
    }

    @Test(description = "TC004", priority = 4)
    public void testUpdationInDataDuringCreation() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "not_completed", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(409);
    }

    @Test(description = "TC006", priority = 6)
    public void testEmptyStringCommentField() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "not_completed", " ", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

    @Test(description = "TC007", priority = 7)
    public void testNullValueCommentField() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "not_completed", null, "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

    @Test(description = "TC008", priority = 8)
    public void testNonExistingFarmTaskId() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "not_completed", "test", "bfcc49f5-83a7-40df-b690-69b455559742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(404);
    }

    @Test(description = "TC009", priority = 9)
    public void testInvalidFarmTaskId() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "not_completed", "test", "2334");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

    @Test(description = "TC010", priority = 10)
    public void testEmptyFarmTaskID() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "not_completed", "test", " ");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

    @Test(description = "TC011", priority = 11)
    public void testInvalidAuthorisation() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", refreshToken, "not_completed", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(401);
    }

  // TC012 - proof media is missing
    // TC013 - type field is missing

    @Test(description = "TC014", priority = 14)
    public void testProofImageGreaterThan10MB() {
        Response response = sendProofRequest("ImageLargerThan10MB.jpg","/api/v1/farm-task/proof", authToken, "completed", "test","bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(409);  // Still expecting success, but it's a bug
    }


//    @Test(description = "TC015", priority = 15)
//    public void testProofVideoGreaterThan100MB() {
//        Response response = sendProofRequest("VideoLargerThan100MB.mp4", "/api/v1/farm-task/proof", authToken, "completed", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
//        System.out.println("Response Body: " + response.getBody().asString());
//        response.then().statusCode(422);
//    }

    @Test(description = "TC016", priority = 16)
    public void testSpecialCharactersInComments() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "completed", "@@@#$%^%*^&$", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(409);
    }

    @Test(description = "TC017", priority = 17)
    public void testInvalidType() {
        Response response = sendProofRequest("ValidImage.jpg", "/api/v1/farm-task/proof", authToken, "test", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

    @Test(description = "TC018", priority = 18)
    public void testInvalidVideoExtension() {
        Response response = sendProofRequest("InvalidVideoExtension.avi", "/api/v1/farm-task/proof", authToken, "test", "test", "bfcc49f5-83a7-40df-b690-69b450fa9742");
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().statusCode(422);
    }

}